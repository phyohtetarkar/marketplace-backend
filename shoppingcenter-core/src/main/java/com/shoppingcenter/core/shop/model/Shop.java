package com.shoppingcenter.core.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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

	@JsonProperty(access = Access.READ_ONLY)
	private ShopContact contact;

	@JsonProperty(access = Access.READ_ONLY)
	private boolean recommended;

	@JsonProperty(access = Access.READ_ONLY)
	private String logo;

	@JsonProperty(access = Access.READ_ONLY)
	private String cover;

	@JsonProperty(access = Access.READ_ONLY)
	private Status status;

	@JsonProperty(access = Access.WRITE_ONLY)
	private long subscriptionPlanId;

	@JsonProperty(access = Access.WRITE_ONLY)
	private UploadFile logoImage;

	@JsonProperty(access = Access.WRITE_ONLY)
	private UploadFile coverImage;

	public static Shop create(ShopEntity entity, String baseUrl) {
		String imageBaseUrl = imageBaseUrl(entity.getSlug(), baseUrl);
		Shop s = createCompat(entity, baseUrl);
		s.setAbout(entity.getAbout());
		s.setStatus(entity.getStatus());
		s.setRecommended(entity.isRecommended());
		s.setLogo(imageBaseUrl + entity.getLogo());
		s.setCover(imageBaseUrl + entity.getCover());
		s.setContact(ShopContact.create(entity.getContact()));
		return s;
	}

	public static Shop createCompat(ShopEntity entity, String baseUrl) {
		Shop s = new Shop();
		s.setId(entity.getId());
		s.setName(entity.getName());
		s.setSlug(entity.getSlug());
		s.setHeadline(entity.getHeadline());
		return s;
	}

	private static String imageBaseUrl(String slug, String baseUrl) {
		return String.format("%s/%s/%s/", baseUrl, "shops", slug);
	}
}
