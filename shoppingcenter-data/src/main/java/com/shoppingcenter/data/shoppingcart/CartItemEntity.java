package com.shoppingcenter.data.shoppingcart;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Constants;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.product.ProductVariantEntity;
import com.shoppingcenter.data.user.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Constants.TABLE_PREFIX + "shoppingcart_item")
public class CartItemEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private Id id;
	
	private int quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private ProductEntity product;
	
	@ManyToOne
	@MapsId("variantId")
	@JoinColumn(name = "variant_id")
	private ProductVariantEntity variant;
	
	public CartItemEntity() {
		this.id = new Id();
	}

	@Getter
	@Setter
	@Embeddable
	public static class Id implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String userId;
		
		private long productId;
		
		private long variantId;
		
	}
}