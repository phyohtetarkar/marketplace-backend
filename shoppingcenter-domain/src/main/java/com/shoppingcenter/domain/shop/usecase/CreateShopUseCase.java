package com.shoppingcenter.domain.shop.usecase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.payment.PaymentGatewayAdapter;
import com.shoppingcenter.domain.payment.PaymentTokenRequest;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.ShopCreateInput;
import com.shoppingcenter.domain.shop.ShopMember;
import com.shoppingcenter.domain.shop.ShopMember.Role;
import com.shoppingcenter.domain.shop.ShopSetting;
import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class CreateShopUseCase {

	private ShopDao shopDao;

	private UserDao userDao;
	
	private SubscriptionPlanDao subscriptionPlanDao;
	
	private ShopSubscriptionDao shopSubscriptionDao;

	private HTMLStringSanitizer htmlStringSanitizer;

	private UpdateShopContactUseCase saveShopContactUseCase;

	private CreateShopMemberUseCase createShopMemberUseCase;
	
	private SaveShopSettingUseCase saveShopSettingUseCase;
	
	private SaveShopAcceptedPaymentUseCase saveShopAcceptedPaymentUseCase;
	
	private SaveShopDeliveryCityUseCase saveShopDeliveryCityUseCase;

	private UploadShopLogoUseCase uploadShopLogoUseCase;

	private UploadShopCoverUseCase uploadShopCoverUseCase;
	
	private PaymentGatewayAdapter paymentGatewayAdapter;

	public void apply(ShopCreateInput data) {
		if (!Utils.hasText(data.getName())) {
			throw new ApplicationException("Required shop name");
		}

		if (!Utils.hasText(data.getSlug())) {
			throw new ApplicationException("Required shop slug");
		}
		
		var subscription = subscriptionPlanDao.findById(data.getSubscriptionPlanId());
		
		if (subscription == null) {
			throw new ApplicationException("Subscription plan not found");
		}
		
		if (!userDao.existsById(data.getUserId())) {
			throw new ApplicationException("User not fond");
		}
		
		var slug = Utils.convertToSlug(data.getName());
		
		if (!Utils.hasText(slug)) {
        	throw new ApplicationException("Invalid slug value");
        }

		if (Utils.hasText(data.getAbout())) {
			var about = data.getAbout();
			data.setAbout(htmlStringSanitizer.sanitize(about));
		}

		data.setSlug(slug);
			
		var acceptedPayments = data.getAcceptedPayments();
		
		
		if (data.isBankTransfer() && (acceptedPayments == null || acceptedPayments.isEmpty())) {
			throw new ApplicationException("Required accepted payments");
		}
		
		if (data.getDeliveryCities() == null || data.getDeliveryCities().isEmpty()) {
			throw new ApplicationException("Required delivery cities");
		}

		var shopId = shopDao.create(data);
		
		var userId = data.getUserId();

		var contact = new ShopContact();
		contact.setAddress(data.getAddress());
		contact.setShopId(shopId);
		saveShopContactUseCase.apply(contact);
		
		var setting = new ShopSetting();
		setting.setShopId(shopId);
		setting.setCashOnDelivery(data.isCashOnDelivery());
		setting.setBankTransfer(data.isBankTransfer());
		
		saveShopSettingUseCase.apply(setting);

		var member = new ShopMember();
		member.setRole(Role.OWNER);
		member.setShopId(shopId);
		member.setUserId(userId);

		createShopMemberUseCase.apply(member);
		
		if (acceptedPayments != null && !acceptedPayments.isEmpty()) {
			saveShopAcceptedPaymentUseCase.apply(shopId, acceptedPayments);
		}
		
		if (data.getDeliveryCities() != null && !data.getDeliveryCities().isEmpty()) {
			saveShopDeliveryCityUseCase.apply(shopId, data.getDeliveryCities());
		}

		if (data.getLogo() != null && !data.getLogo().isEmpty()) {
			uploadShopLogoUseCase.apply(shopId, data.getLogo());
		}

		if (data.getCover() != null && !data.getCover().isEmpty()) {
			uploadShopCoverUseCase.apply(shopId, data.getCover());
		}
		
		var shopSubscription = new ShopSubscription();
		shopSubscription.setTitle(subscription.getTitle());
		shopSubscription.setSubTotalPrice(subscription.getPrice());
		// TODO: check promo
		
		shopSubscription.setTotalPrice(shopSubscription.getSubTotalPrice().subtract(shopSubscription.getDiscount()));
		shopSubscription.setDuration(subscription.getDuration());
		shopSubscription.setShopId(shopId);
		
		var start = LocalDate.now();
		
		shopSubscription.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
		shopSubscription.setEndAt(start.plusDays(subscription.getDuration() - 1).atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
		
		var shopSubscriptionId = shopSubscriptionDao.save(shopSubscription);
		
		var paymentRequest = new PaymentTokenRequest();
		paymentRequest.setAmount(shopSubscription.getTotalPrice());
		paymentRequest.setInvoiceNo(String.valueOf(shopSubscriptionId));
		paymentRequest.setDescription(String.format("Subscribe to %s", subscription.getTitle()));
		
		var result = paymentGatewayAdapter.requestPaymentToken(paymentRequest);
	}

}
