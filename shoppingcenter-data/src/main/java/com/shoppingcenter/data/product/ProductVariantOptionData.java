package com.shoppingcenter.data.product;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ProductVariantOptionData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String option;

	private String value;

	@Override
	public int hashCode() {
		return Objects.hash(option, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductVariantOptionData other = (ProductVariantOptionData) obj;
		return Objects.equals(option, other.option) && Objects.equals(value, other.value);
	}

}
