package com.marketplace.data.shop;

import java.util.List;

import com.marketplace.data.AuditingEntity;
import com.marketplace.data.discount.DiscountEntity;
import com.marketplace.data.general.CityEntity;
import com.marketplace.data.review.ShopReviewEntity;
import com.marketplace.domain.Constants;
import com.marketplace.domain.shop.Shop;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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

	@Column(columnDefinition = "TEXT", unique = true)
	private String slug;

	@Column(columnDefinition = "TEXT")
	private String headline;

	private String logo;

	private String cover;

//	@Column(columnDefinition = "TEXT")
	@Lob
    @Basic(fetch = FetchType.LAZY)
	private String about;

	private boolean featured;
	
	private boolean deleted;
    
    private long expiredAt;
    
    @Enumerated(EnumType.STRING)
    private Shop.Status status;

	@Version
	private long version;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private ShopContactEntity contact;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private ShopSettingEntity setting;
	
	@OneToOne(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private ShopRatingEntity rating;
	
	@ManyToOne(fetch = FetchType.LAZY)
    private CityEntity city;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ShopMemberEntity> members;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<DiscountEntity> discounts;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ShopReviewEntity> reviews;
	
	@OneToMany(mappedBy = "shop", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ShopAcceptedPaymentEntity> payments;

	public ShopEntity() {
		this.status = Shop.Status.PENDING;
	}

}
