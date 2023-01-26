package com.shoppingcenter.data.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.user.UserEntity;

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
