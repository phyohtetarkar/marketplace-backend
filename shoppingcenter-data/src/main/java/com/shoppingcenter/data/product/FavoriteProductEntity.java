package com.shoppingcenter.data.product;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.user.UserEntity;

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
@Entity(name = "FavoriteProduct")
@Table(name = Entities.TABLE_PREFIX + "favorite_product")
public class FavoriteProductEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(optional = false)
	private ProductEntity product;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private UserEntity user;

	public FavoriteProductEntity() {
	}

}
