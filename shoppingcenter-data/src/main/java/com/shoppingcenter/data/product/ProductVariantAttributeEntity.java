package com.shoppingcenter.data.product;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ProductVariantAttributeEntity {

	private long attributeId;
	
	private String attribute;

	private String value;

	private int sort;

	public ProductVariantAttributeEntity() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(attributeId, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductVariantAttributeEntity other = (ProductVariantAttributeEntity) obj;
		return attributeId == other.attributeId && Objects.equals(value, other.value);
	}

}
