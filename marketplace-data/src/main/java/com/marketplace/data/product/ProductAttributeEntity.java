package com.marketplace.data.product;

import java.io.Serializable;
import java.util.Objects;

import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProductAttribute")
@Table(name = Constants.TABLE_PREFIX + "product_attribute")
public class ProductAttributeEntity {

	@EmbeddedId
	private ProductAttributeEntity.ID id;
	
	private int sort;
	
	@MapsId("productId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private ProductEntity product;
	
	public ProductAttributeEntity() {
		this.id = new ID();
	}
	
	public String getName() {
		return id.getName();
	}
	
	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {
		
		private static final long serialVersionUID = 1L;

		@Column(name = "product_id")
		private long productId;

		/**
		 * color, size, capacity, etc.
		 */
		private String name;

		public ID() {
		}

		public ID(long productId, String name) {
			super();
			this.productId = productId;
			this.name = name;
		}

		@Override
		public int hashCode() {
			return Objects.hash(name, productId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ID other = (ID) obj;
			return Objects.equals(name, other.name) && productId == other.productId;
		}

	}
	
}
