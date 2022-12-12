package com.shoppingcenter.core.shop;

import com.shoppingcenter.core.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopEdit {

	private long id;

	private String name;

	private String slug;
	
	private String headline;

	private String address;
	
	private String about;
	
	private UploadFile logo;
	
	private UploadFile cover;
}
