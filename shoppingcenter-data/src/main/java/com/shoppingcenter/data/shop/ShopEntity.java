package com.shoppingcenter.data.shop;

import java.util.List;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.discount.DiscountEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Shop")
@Table(name = Entities.TABLE_PREFIX + "shop")
public class ShopEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String name;

	@Column(columnDefinition = "TEXT", unique = true)
	private String slug;

	@Column(columnDefinition = "TEXT")
	private String headline;

	@Column(columnDefinition = "TEXT")
	private String logo;

	@Column(columnDefinition = "TEXT")
	private String cover;

	@Column(columnDefinition = "TEXT")
	private String about;

	private String status;

	private boolean featured;

	private double rating;

	@Version
	private long version;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private ShopContactEntity contact;

	// @OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE)
	// private List<ShopBranchEntity> branches;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ShopMemberEntity> members;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<DiscountEntity> discounts;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ShopReviewEntity> reviews;

	// @OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval =
	// true)
	// private List<ShopSubscriptionEntity> subscriptions;

	public ShopEntity() {
	}

}
