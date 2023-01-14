package com.shoppingcenter.data.shop;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.discount.DiscountEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Shop")
@Table(name = Entities.TABLE_PREFIX + "shop")
public class ShopEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	public enum Status {
		PENDING, ACTIVE, SUBSCRIPTION_EXPIRED, DENIED
	}

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

	@Enumerated(EnumType.STRING)
	private Status status;

	private boolean featured;

	private double rating;

	@Version
	private long version;

	// @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	// private List<ShopSocialPageEntity> socialPages;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.REMOVE)
	private ShopContactEntity contact;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE)
	private List<ShopBranchEntity> branches;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE)
	private List<ShopMemberEntity> members;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE)
	private List<DiscountEntity> discounts;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE)
	private List<ShopReviewEntity> reviews;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE)
	private List<ShopSubscriptionEntity> subscriptions;

	public ShopEntity() {
		this.status = Status.PENDING;
	}

}
