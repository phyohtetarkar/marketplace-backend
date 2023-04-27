package com.shoppingcenter.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Audit {

	private long createdAt;
	
	private long updatedAt;
	
	public Audit() {
	}

	public Audit(long createdAt, long updatedAt) {
		super();
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
}
