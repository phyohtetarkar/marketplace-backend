package com.shoppingcenter.data.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Constants.TABLE_PREFIX + "shop_social_page")
public class ShopSocialPageEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;
	
	public enum Platform {
		FACEBOOK, TWITTER, INSTAGRAM
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(columnDefinition = "TEXT")
	private String pageId;
	
	@Enumerated(EnumType.STRING)
	private Platform platform;

	@ManyToOne(fetch = FetchType.LAZY)
	private ShopEntity shop;
	
	public ShopSocialPageEntity() {
	}
}
