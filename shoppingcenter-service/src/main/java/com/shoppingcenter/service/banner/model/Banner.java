package com.shoppingcenter.service.banner.model;

import org.springframework.util.StringUtils;

import com.shoppingcenter.data.banner.BannerEntity;
import com.shoppingcenter.service.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Banner {

	private int id;

	private String image;

	private String link;

	private int position;

	private UploadFile file;

	private long createdAt;

	public static Banner create(BannerEntity entity, String baseUrl) {
		Banner b = new Banner();
		b.setId(entity.getId());
		b.setLink(entity.getLink());
		b.setPosition(entity.getPosition());
		b.setCreatedAt(entity.getCreatedAt());
		if (StringUtils.hasText(entity.getImage())) {
			b.setImage(baseUrl + "banner/" + entity.getImage());
		}
		return b;
	}
}