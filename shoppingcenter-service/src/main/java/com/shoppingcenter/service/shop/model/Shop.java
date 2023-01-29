package com.shoppingcenter.service.shop.model;

import org.springframework.util.StringUtils;

import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.service.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop {

	public enum Status {
		PENDING, ACTIVE, SUBSCRIPTION_EXPIRED, DENIED
	}

	private long id;

	private String name;

	private String slug;

	private String headline;

	private String about;

	private double rating;

	private long createdAt;

	private ShopContact contact;

	private boolean featured;

	private String logo;

	private String cover;

	private Status status;

	private String address;

	private long subscriptionPlanId;

	private UploadFile logoImage;

	private UploadFile coverImage;

	public Shop() {
		this.status = Status.PENDING;
	}

	public static Shop create(ShopEntity entity, String baseUrl) {
		Shop s = createCompat(entity, baseUrl);
		s.setAbout(entity.getAbout());
		s.setContact(ShopContact.create(entity.getContact()));
		return s;
	}

	public static Shop createCompat(ShopEntity entity, String baseUrl) {
		String imageBaseUrl = imageBaseUrl(entity.getSlug(), baseUrl);
		Shop s = new Shop();
		s.setId(entity.getId());
		s.setName(entity.getName());
		s.setSlug(entity.getSlug());
		s.setHeadline(entity.getHeadline());
		s.setFeatured(entity.isFeatured());
		s.setRating(entity.getRating());
		s.setCreatedAt(entity.getCreatedAt());
		s.setStatus(Status.valueOf(entity.getStatus()));
		if (StringUtils.hasText(entity.getLogo())) {
			s.setLogo(imageBaseUrl + entity.getLogo());
		}

		if (StringUtils.hasText(entity.getCover())) {
			s.setCover(imageBaseUrl + entity.getCover());
		}
		return s;
	}

	private static String imageBaseUrl(String slug, String baseUrl) {
		return String.format("%s/%s/", baseUrl, "shop");
	}
}
