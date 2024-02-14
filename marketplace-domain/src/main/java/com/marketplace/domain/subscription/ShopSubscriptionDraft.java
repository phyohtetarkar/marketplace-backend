package com.marketplace.domain.subscription;

import java.math.BigDecimal;

import com.marketplace.domain.Audit;
import com.marketplace.domain.shop.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscriptionDraft {

	private long id;

	private String title;

	private BigDecimal subTotalPrice;

	private BigDecimal discount;

	private BigDecimal totalPrice;

	private int duration;

	private String promoCode;

	private Shop shop;

	private Audit audit = new Audit();
	
	public ShopSubscriptionDraft() {
		this.subTotalPrice =  BigDecimal.ZERO;
		this.totalPrice =  BigDecimal.ZERO;
		this.discount = BigDecimal.ZERO;
	}
	
	public ShopSubscription toShopSubscription() {
		var ss = new ShopSubscription();
		ss.setInvoiceNo(id);
		ss.setTitle(title);
		ss.setSubTotalPrice(subTotalPrice);
		ss.setDiscount(discount);
		ss.setTotalPrice(totalPrice);
		ss.setDuration(duration);
		ss.setPromoCode(promoCode);
		ss.setShop(shop);
		return ss;
	}
}
