package com.shoppingcenter.data.product.variant;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ProductVariantOptionData implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "variant_id")
	private long variantId;

	private String option;

	private String value;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (variantId ^ (variantId >>> 32));
		result = prime * result + ((option == null) ? 0 : option.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
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
		if (variantId != other.variantId)
			return false;
		if (option == null) {
			if (other.option != null)
				return false;
		} else if (!option.equals(other.option))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
