package com.shoppingcenter.data.shop;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Constants;
import com.shoppingcenter.data.user.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Constants.TABLE_PREFIX + "shop_review")
public class ShopReviewEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private Id id;
	
	private double rating;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("shopId")
	@JoinColumn(name = "shop_id")
	private ShopEntity shop;
	
	public ShopReviewEntity() {
		this.id = new Id();
	}
	
	@Getter
	@Setter
	@Embeddable
	public static class Id implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String userId;
		
		private long shopId;
		
	}

}
