package com.shoppingcenter.domain.banner;

import com.shoppingcenter.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Banner {

	private int id;

	private String image;

	private String imageUrl;

	private String link;

	private int position;

	private UploadFile file;

	private Long createdAt;
}
