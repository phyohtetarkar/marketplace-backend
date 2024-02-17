package com.marketplace.domain.banner;

import com.marketplace.domain.Audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Banner {

	private int id;

	private String image;

	private String link;

	private int position;
	
	private Audit audit = new Audit();

}
