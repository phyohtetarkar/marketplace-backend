package com.shoppingcenter.domain.shop.usecase;

import java.util.Arrays;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.payment.PaymentTokenResponse;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.ShopCreateInput;
import com.shoppingcenter.domain.shop.ShopMember;
import com.shoppingcenter.domain.shop.ShopMember.Role;
import com.shoppingcenter.domain.shop.ShopSetting;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class CreateShopUseCase {

	private ShopDao shopDao;

	private UserDao userDao;

	private HTMLStringSanitizer htmlStringSanitizer;

	private UpdateShopContactUseCase saveShopContactUseCase;

	private CreateShopMemberUseCase createShopMemberUseCase;
	
	private SaveShopSettingUseCase saveShopSettingUseCase;
	
	private SaveShopAcceptedPaymentUseCase saveShopAcceptedPaymentUseCase;
	
	private SaveShopDeliveryCityUseCase saveShopDeliveryCityUseCase;

	private UploadShopLogoUseCase uploadShopLogoUseCase;

	private UploadShopCoverUseCase uploadShopCoverUseCase;
	
	public PaymentTokenResponse apply(ShopCreateInput data) {
		if (!Utils.hasText(data.getName())) {
			throw new ApplicationException("Required shop name");
		}

		if (!Utils.hasText(data.getSlug())) {
			throw new ApplicationException("Required shop slug");
		}
		
		if (!Utils.hasText(data.getPhone())) {
			throw new ApplicationException("Required phone number");
		}
		
//		var subscription = subscriptionPlanDao.findById(data.getSubscriptionPlanId());
//		
//		if (subscription == null) {
//			throw new ApplicationException("Subscription plan not found");
//		}
		
		if (!userDao.existsById(data.getUserId())) {
			throw new ApplicationException("User not fond");
		}
		
		if (Utils.hasText(data.getAbout())) {
			var about = data.getAbout();
			data.setAbout(htmlStringSanitizer.sanitize(about));
		}

		var acceptedPayments = data.getAcceptedPayments();
		
		
		if (data.isBankTransfer() && (acceptedPayments == null || acceptedPayments.isEmpty())) {
			throw new ApplicationException("Required accepted payments");
		}
		
		if (data.getDeliveryCities() == null || data.getDeliveryCities().isEmpty()) {
			throw new ApplicationException("Required delivery cities");
		}

		var shopId = shopDao.create(data);
		
		var userId = data.getUserId();

//		var shopSubscription = new ShopSubscription();
//		shopSubscription.setTitle(subscription.getTitle());
//		shopSubscription.setSubTotalPrice(subscription.getPrice());
//		shopSubscription.setTotalPrice(shopSubscription.getSubTotalPrice().subtract(shopSubscription.getDiscount()));
//		shopSubscription.setDuration(subscription.getDuration());
//		shopSubscription.setShopId(shopId);
//		
//		var start = LocalDate.now();
//		
//		shopSubscription.setStartAt(start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
//		shopSubscription.setEndAt(start.plusDays(subscription.getDuration() - 1).atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
//		
//		if (shopSubscription.getTotalPrice().doubleValue() <= 0) {
//			shopSubscription.setStatus(Status.SUCCESS);
//		}
//		
//		var shopSubscriptionId = shopSubscriptionDao.save(shopSubscription);
//		
//		PaymentTokenResponse result = null;
		
//		if (shopSubscription.getTotalPrice().doubleValue() > 0) {
////			var percentage = 10;
////			var transactionFee = shopSubscription.getTotalPrice().multiply(BigDecimal.valueOf(percentage))
////					.divide(BigDecimal.valueOf(100));
//			
//			var paymentRequest = new PaymentTokenRequest();
//			paymentRequest.setAmount(shopSubscription.getTotalPrice());
//			paymentRequest.setInvoiceNo(String.valueOf(shopSubscriptionId));
//			paymentRequest.setDescription(String.format("%s %d days subscription", subscription.getTitle(), shopSubscription.getDuration()));
//			
//			result = paymentGatewayAdapter.requestPaymentToken(paymentRequest);
//			
//			if (!"0000".equals(result.getRespCode())) {
//				throw new ApplicationException("Something went wrong. Please try again");
//			}
//			
//			return result;
//		} 

		
		var contact = new ShopContact();
		contact.setAddress(data.getAddress());
		contact.setPhones(Arrays.asList(data.getPhone()));
		contact.setShopId(shopId);
		saveShopContactUseCase.apply(contact);
		
		var setting = new ShopSetting();
		setting.setShopId(shopId);
		setting.setCashOnDelivery(true);
		//setting.setBankTransfer(data.isBankTransfer());
		
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
		
		return null;
	}

}
