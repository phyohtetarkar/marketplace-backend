package com.shoppingcenter.data.shop;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.user.UserEntity;

import jakarta.persistence.Column;
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
@Entity(name = "ShopReview")
@Table(name = Entities.TABLE_PREFIX + "shop_review")
public class ShopReviewEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private double rating;

	@Column(columnDefinition = "TEXT")
	private String description;

	@ManyToOne(optional = false)
	private UserEntity user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ShopEntity shop;

	public ShopReviewEntity() {
	}

}
