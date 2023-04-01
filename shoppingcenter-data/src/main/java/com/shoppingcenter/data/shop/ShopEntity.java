package com.shoppingcenter.data.shop;

import java.util.List;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.discount.DiscountEntity;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.shop.Shop;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = Constants.TABLE_PREFIX + "shop")
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

	// @Column(columnDefinition = "TEXT")
	private String logo;

	// @Column(columnDefinition = "TEXT")
	private String cover;

	@Column(columnDefinition = "TEXT")
	private String about;

	@Enumerated(EnumType.STRING)
	private Shop.Status status;

	private boolean featured;

	private boolean disabled;

	private boolean expired;

	private double rating;

	private int pendingOrderCount;

	@Version
	private long version;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private ShopContactEntity contact;

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
