package com.marketplace.domain.subscription.usecase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.payment.TCTPPaymentGatewayAdapter;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.subscription.ShopSubscription;
import com.marketplace.domain.subscription.ShopSubscriptionTime;
import com.marketplace.domain.subscription.ShopSubscription.Status;
import com.marketplace.domain.subscription.dao.ShopSubscriptionDao;
import com.marketplace.domain.subscription.dao.ShopSubscriptionDraftDao;
import com.marketplace.domain.subscription.dao.ShopSubscriptionTransactionDao;
import com.marketplace.domain.subscription.ShopSubscriptionTransaction;

@Component
public class CompleteShopSubscriptionUseCase {

	@Autowired
	private ShopDao shopDao;

	@Autowired
	private ShopSubscriptionDao shopSubscriptionDao;

	@Autowired
	private ShopSubscriptionDraftDao shopSubscriptionDraftDao;

	@Autowired
	private ShopSubscriptionTransactionDao shopSubscriptionTransactionDao;

	@Autowired
	private TCTPPaymentGatewayAdapter tctpPaymentGatewayAdapter;

	@Retryable(noRetryFor = { ApplicationException.class })
	@Transactional
	public void apply(String payload) {
		var result = tctpPaymentGatewayAdapter.decodeResultPayload(payload);

		if (result == null) {
			return;
		}

		var trans = new ShopSubscriptionTransaction(result);

		var shopSubscription = shopSubscriptionDao.findByInvoiceNo(trans.getInvoiceNo());

		if (shopSubscription == null) {

			var draft = shopSubscriptionDraftDao.findById(trans.getInvoiceNo());

			if (draft == null) {
				return;
			}

			shopSubscription = draft.toShopSubscription();
		}

		var shopId = shopSubscription.getShop().getId();
		
		ShopSubscriptionTime time = null;

		if ("0000".equals(trans.getRespCode())) {
			shopSubscription.setStatus(ShopSubscription.Status.SUCCESS);

			time = new ShopSubscriptionTime();
			time.setInvoiceNo(shopSubscription.getInvoiceNo());
			time.setShopId(shopId);

			var latestSubscription = shopSubscriptionDao.findLatestSubscriptionByShop(shopId);

			var duration = shopSubscription.getDuration();

			if (latestSubscription == null) {
				var start = LocalDate.now();

				time.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
				time.setEndAt(start.plusDays(duration - 1).atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toInstant()
						.toEpochMilli());

			} else {
				var start = Instant.ofEpochMilli(latestSubscription.getEndAt()).atZone(ZoneOffset.UTC).toLocalDate()
						.plusDays(1);

				time.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
				time.setEndAt(start.plusDays(duration - 1).atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toInstant()
						.toEpochMilli());

			}
		} else if ("0001".equals(trans.getRespCode()) || "2001".equals(trans.getRespCode())) {
			shopSubscription.setStatus(ShopSubscription.Status.PENDING);
		} else {
			shopSubscription.setStatus(ShopSubscription.Status.FAILED);
		}

		shopSubscriptionDao.save(shopSubscription);
		
		if (time != null) {
			shopSubscriptionDao.saveTime(time);
		}

		shopSubscriptionTransactionDao.save(trans);

		var status = shopSubscription.getStatus();

		if (status == Status.SUCCESS && time != null) {
			shopDao.updateExpiredAt(shopId, time.getEndAt());
		}

	}

}
