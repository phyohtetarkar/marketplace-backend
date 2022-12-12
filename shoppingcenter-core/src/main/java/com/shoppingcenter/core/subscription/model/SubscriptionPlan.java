package com.shoppingcenter.core.subscription.model;

import com.shoppingcenter.data.subscription.SubscriptionPlanEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionPlan {

	private long id;

	public static SubscriptionPlan create(SubscriptionPlanEntity entity) {
		SubscriptionPlan sp = new SubscriptionPlan();
		return sp;
	}
}
