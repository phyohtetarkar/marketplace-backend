package com.shoppingcenter.domain.banner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Banner {

	private int id;

	private String image;

	private String link;

	private int position;

	private long createdAt;

}
