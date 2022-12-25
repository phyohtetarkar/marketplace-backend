package com.shoppingcenter.data.shop;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Constants;
import com.shoppingcenter.data.LocationData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Constants.TABLE_PREFIX + "shop")
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

	@Column(columnDefinition = "TEXT")
	private String phones; // comma separated

	@Column(columnDefinition = "TEXT", unique = true)
	private String slug;

	@Column(columnDefinition = "TEXT")
	private String headline;

	@Column(columnDefinition = "TEXT")
	private String logo;

	@Column(columnDefinition = "TEXT")
	private String cover;

	@Column(columnDefinition = "TEXT")
	private String address;

	@Column(columnDefinition = "TEXT")
	private String about;

	@Enumerated(EnumType.STRING)
	private Status status;

	private boolean recommended;

	private double rating;

	@Embedded
	private LocationData location;

	// @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	// private List<ShopSocialPageEntity> socialPages;

	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	private List<ShopBranchEntity> branches;

	public ShopEntity() {
		this.status = Status.PENDING;
		this.location = new LocationData();
	}

}
