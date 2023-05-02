package com.shoppingcenter.data.product;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ProductAttributeValueEntity {

	private String value;

	private int sort;

	public ProductAttributeValueEntity() {
	}
	
	public ProductAttributeValueEntity(String value, int sort) {
		super();
		this.value = value;
		this.sort = sort;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductAttributeValueEntity other = (ProductAttributeValueEntity) obj;
		return Objects.equals(value, other.value);
	}


}
