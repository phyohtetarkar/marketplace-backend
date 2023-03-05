package com.shoppingcenter.data.product;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ProductOptionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "product_id")
	private long productId;

	private String name;

	private int position;

	public ProductOptionEntity() {
	}

	public ProductOptionEntity(String name, int position) {
		this.name = name;
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (productId ^ (productId >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + position;
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
		ProductOptionEntity other = (ProductOptionEntity) obj;
		if (productId != other.productId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (position != other.position)
			return false;
		return true;
	}

}
