package com.shoppingcenter.data.shop;

import java.math.BigDecimal;
import java.util.List;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.discount.DiscountEntity;
import com.shoppingcenter.domain.Constants;

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
@Table(name = Constants.TABLE_PREFIX + "shop")
public class ShopEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String name;

	@Column(columnDefinition = "TEXT")
	private String slug;

	@Column(columnDefinition = "TEXT")
	private String headline;

	private String logo;

	private String cover;

	@Column(columnDefinition = "TEXT")
	private String about;
	
	@Column(columnDefinition = "TEXT")
	private String deliveryNote;

	private boolean featured;
	
	private boolean disabled;
	
	private boolean activated;
    
    private boolean expired;

	@Column(precision = 2, scale = 1, nullable = false)
	private BigDecimal rating;
	
	@Version
	private long version;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private ShopContactEntity contact;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private ShopSettingEntity setting;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ShopMemberEntity> members;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<DiscountEntity> discounts;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ShopReviewEntity> reviews;
	
	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ShopDeliveryCityEntity> deliveryCities;
	
	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ShopAcceptedPaymentEntity> payments;

	// @OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval =
	// true)
	// private List<ShopSubscriptionEntity> subscriptions;

	public ShopEntity() {
		this.rating = new BigDecimal(0);
	}

}
