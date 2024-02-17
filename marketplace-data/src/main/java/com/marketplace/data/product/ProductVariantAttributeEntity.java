package com.marketplace.data.product;

import java.io.Serializable;
import java.util.Objects;

import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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
@Entity(name = "ProductVariantAttribute")
@Table(name = Constants.TABLE_PREFIX + "product_variant_attribute")
public class ProductVariantAttributeEntity {

	@EmbeddedId
	private ProductVariantAttributeEntity.ID id;

	/**
	 * Attribute value: Red, XXL, 64GB, etc.
	 */
	private String value;

	private int sort;
	
	private int vSort;
	
//	@MapsId("productId")
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "product_id")
//	private ProductEntity product;
//	
	@MapsId("variantId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "variant_id")
	private ProductVariantEntity variant;

	public ProductVariantAttributeEntity() {
		this.id = new ID();
	}
	
	public String getAttribute() {
		return id.getAttributeId().getName();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Embedded
		private ProductAttributeEntity.ID attributeId;

		@Column(name = "variant_id")
		private long variantId;

		public ID() {
			
		}

		public ID(ProductAttributeEntity.ID attributeId, long variantId) {
			super();
			this.attributeId = attributeId;
			this.variantId = variantId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(attributeId, variantId);
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
			return Objects.equals(attributeId, other.attributeId) && variantId == other.variantId;
		}

	}
}
