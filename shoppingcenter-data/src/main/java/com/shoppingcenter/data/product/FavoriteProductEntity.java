package com.shoppingcenter.data.product;

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
import com.shoppingcenter.data.Utils;
import com.shoppingcenter.data.user.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "FavoriteProduct")
@Table(name = Utils.TABLE_PREFIX + "favorite_product")
public class FavoriteProductEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Id id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private ProductEntity product;

	public FavoriteProductEntity() {
		this.id = new Id();
	}

	@Getter
	@Setter
	@Embeddable
	public static class Id implements Serializable {

		private static final long serialVersionUID = 1L;

		private String userId;

		private long productId;

	}

}
