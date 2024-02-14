package com.marketplace.domain.category;

import java.util.List;

import com.marketplace.domain.Audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

	private int id;

	private int lft;

	private int rgt;

	private String name;

	private String slug;

	private String image;

	private boolean featured;

	private Category category;

	private List<Category> children;

	private List<CategoryName> names;

	private Audit audit = new Audit();

	public String getName() {
		if (name == null && names != null) {
			return names.stream().filter(n -> n.getLang().equals("en")).findFirst()
					.map(CategoryName::getName)
					.orElse(null);
		}
		return name;
	}

}
