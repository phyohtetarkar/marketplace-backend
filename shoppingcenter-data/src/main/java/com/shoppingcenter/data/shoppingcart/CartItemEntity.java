package com.shoppingcenter.data.shoppingcart;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.data.variant.ProductVariantEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CartItem")
@Table(name = Entities.TABLE_PREFIX + "cart_item")
public class CartItemEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ID id;

	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	private UserEntity user;

	@ManyToOne
	@MapsId("productId")
	private ProductEntity product;

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false),
			@JoinColumn(name = "option_path", referencedColumnName = "option_path", insertable = false, updatable = false)
	})
	private ProductVariantEntity variant;

	public CartItemEntity() {
		this.id = new ID();
	}

	@Getter
	@Setter
	@EqualsAndHashCode
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "user_id")
		private String userId;

		@Column(name = "proudct_id")
		private long productId;

		@Column(name = "option_path")
		private String optionPath;

		public ID() {

		}
	}
}
