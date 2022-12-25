package com.shoppingcenter.core.banner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.data.banner.BannerEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Banner {

	private int id;

	@JsonProperty(access = Access.READ_ONLY)
	private String image;

	private String link;

	private int position;

	@JsonProperty(access = Access.WRITE_ONLY)
	private UploadFile file;

	public static Banner create(BannerEntity entity, String baseUrl) {
		Banner b = new Banner();
		b.setId(entity.getId());
		b.setImage(baseUrl + "banners/" + entity.getImage());
		b.setLink(entity.getLink());
		b.setPosition(entity.getPosition());
		return b;
	}
}
