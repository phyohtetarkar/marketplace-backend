package com.shoppingcenter.core.shop.model;

import org.springframework.util.StringUtils;

import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopEntity.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop {

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

	public static Shop create(ShopEntity entity, String baseUrl) {
		Shop s = createCompat(entity, baseUrl);
		s.setAbout(entity.getAbout());
		s.setStatus(entity.getStatus());
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
		if (StringUtils.hasText(entity.getLogo())) {
			s.setLogo(imageBaseUrl + entity.getLogo());
		}

		if (StringUtils.hasText(entity.getCover())) {
			s.setCover(imageBaseUrl + entity.getCover());
		}
		return s;
	}

	private static String imageBaseUrl(String slug, String baseUrl) {
		return String.format("%s/%s/%s/", baseUrl, "shops", slug);
	}
}
