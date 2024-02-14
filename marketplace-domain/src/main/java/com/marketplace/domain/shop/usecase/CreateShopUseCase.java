package com.marketplace.domain.shop.usecase;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.HTMLStringSanitizer;
import com.marketplace.domain.general.CityDao;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopContactInput;
import com.marketplace.domain.shop.ShopCreateInput;
import com.marketplace.domain.shop.ShopMember.Role;
import com.marketplace.domain.shop.ShopMemberInput;
import com.marketplace.domain.shop.ShopRating;
import com.marketplace.domain.shop.ShopSettingInput;
import com.marketplace.domain.shop.ShopStatusHistory;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.shop.dao.ShopRatingDao;
import com.marketplace.domain.shop.dao.ShopStatusHistoryDao;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class CreateShopUseCase {

	@Autowired
	private ShopDao shopDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private ShopRatingDao shopRatingDao;
	
	@Autowired
	private ShopStatusHistoryDao shopStatusHistoryDao;

	@Autowired
	private HTMLStringSanitizer htmlStringSanitizer;

	@Autowired
	private UpdateShopContactUseCase saveShopContactUseCase;

	@Autowired
	private CreateShopMemberUseCase createShopMemberUseCase;
	
	@Autowired
	private SaveShopSettingUseCase saveShopSettingUseCase;
	
	@Autowired
	private SaveShopAcceptedPaymentUseCase saveShopAcceptedPaymentUseCase;

	@Autowired
	private UploadShopLogoUseCase uploadShopLogoUseCase;

	@Autowired
	private UploadShopCoverUseCase uploadShopCoverUseCase;
	
	@Transactional
	public void apply(ShopCreateInput values) {
		var rawSlug = Utils.convertToSlug(values.getSlug());
		if (!Utils.hasText(values.getName())) {
			throw new ApplicationException("Required shop name");
		}

		if (!Utils.hasText(rawSlug)) {
			throw new ApplicationException("Required shop slug");
		}
		
		if (!Utils.hasText(values.getPhone())) {
			throw new ApplicationException("Required phone number");
		}
		
		if (!userDao.existsById(values.getUserId())) {
			throw new ApplicationException("User not found");
		}
		
		if (!cityDao.existsById(values.getCityId())) {
			throw new ApplicationException("City not found");
		}
		
		values.setAbout(htmlStringSanitizer.sanitize(values.getAbout()));
		
		var slug = Utils.generateSlug(rawSlug, v -> shopDao.existsBySlug(v));
    	values.setSlug(slug);

		var acceptedPayments = values.getAcceptedPayments();
		
		if (values.isBankTransfer() && (acceptedPayments == null || acceptedPayments.isEmpty())) {
			throw new ApplicationException("Required accepted payments");
		}

		var shopId = shopDao.create(values);
		
		var userId = values.getUserId();
		
		var rating = new ShopRating();
		rating.setShopId(shopId);
		
		shopRatingDao.save(rating);
		
		var contact = new ShopContactInput();
		contact.setAddress(values.getAddress());
		contact.setPhones(Arrays.asList(values.getPhone()));
		contact.setShopId(shopId);
		contact.setCityId(values.getCityId());
		saveShopContactUseCase.apply(contact);
		
		var setting = new ShopSettingInput();
		setting.setShopId(shopId);
		setting.setCashOnDelivery(true);
		
		saveShopSettingUseCase.apply(setting);

		var member = new ShopMemberInput();
		member.setRole(Role.OWNER);
		member.setShopId(shopId);
		member.setUserId(userId);

		createShopMemberUseCase.apply(member);
		
		if (acceptedPayments != null && !acceptedPayments.isEmpty()) {
			saveShopAcceptedPaymentUseCase.apply(shopId, acceptedPayments);
		}
		
		if (values.getLogo() != null && !values.getLogo().isEmpty()) {
			uploadShopLogoUseCase.apply(shopId, values.getLogo());
		}

		if (values.getCover() != null && !values.getCover().isEmpty()) {
			uploadShopCoverUseCase.apply(shopId, values.getCover());
		}
		
		var history = new ShopStatusHistory();
		history.setShopId(shopId);
		history.setStatus(Shop.Status.PENDING);
		history.setRemark("Shop created");
		
		shopStatusHistoryDao.save(history);
	}

}
