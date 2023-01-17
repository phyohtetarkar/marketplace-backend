package com.shoppingcenter.data.shoppingcart;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.data.variant.ProductVariantEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CartItem")
@Table(name = Entities.TABLE_PREFIX + "cart_item")
public class CartItemEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private UserEntity user;

	@ManyToOne(optional = false)
	private ProductEntity product;

	@ManyToOne
	private ProductVariantEntity variant;

	public CartItemEntity() {
	}

	@PrePersist
	private void prePersist() {
		if (variant != null) {
			this.id = String.format("%s:%s", user.getId(), variant.getId());
		} else {
			this.id = String.format("%s:%d", user.getId(), product.getId());
		}

	}

}
