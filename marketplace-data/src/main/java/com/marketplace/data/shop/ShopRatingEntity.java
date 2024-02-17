package com.marketplace.data.shop;

import java.math.BigDecimal;

import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopRating")
@Table(name = Constants.TABLE_PREFIX + "shop_rating")
public class ShopRatingEntity {

	@Id
	private long id;
	
	@Column(precision = 2, scale = 1, nullable = false)
	private BigDecimal rating;
	
	private int count;
	
	@Version
	private long version;
	
	@MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ShopEntity shop;
	
	public ShopRatingEntity() {
		this.rating = BigDecimal.ZERO;
	}
}
