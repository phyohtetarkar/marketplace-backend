package com.shoppingcenter.data.shoppingcart;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
