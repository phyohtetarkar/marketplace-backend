package com.shoppingcenter.data.shoppingcart;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.product.ProductVariantEntity;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Entity;
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
@Table(name = Constants.TABLE_PREFIX + "cart_item")
public class CartItemEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int quantity;

	@ManyToOne
	private UserEntity user;

	@ManyToOne
	private ProductEntity product;

	@ManyToOne
	private ProductVariantEntity variant;

	public CartItemEntity() {
	}
}
