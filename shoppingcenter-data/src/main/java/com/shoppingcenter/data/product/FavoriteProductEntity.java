package com.shoppingcenter.data.product;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.user.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "FavoriteProduct")
@Table(name = Entities.TABLE_PREFIX + "favorite_product")
public class FavoriteProductEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@ManyToOne(optional = false)
	private ProductEntity product;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private UserEntity user;

	public FavoriteProductEntity() {
	}

	@PrePersist
	private void prePersist() {
		this.id = String.format("%d:%s", product.getId(), user.getId());
	}

}
