package com.shoppingcenter.data.shoppingcart;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.data.variant.ProductVariantEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CartItem")
@Table(name = Entities.TABLE_PREFIX + "cart_item")
public class CartItemEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private UserEntity user;

	@ManyToOne(optional = false)
	private ProductEntity product;

	@ManyToOne
	private ProductVariantEntity variant;

	public CartItemEntity() {
	}

}
