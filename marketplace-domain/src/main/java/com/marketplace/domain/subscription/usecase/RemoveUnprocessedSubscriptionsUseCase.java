package com.marketplace.domain.subscription.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.subscription.dao.ShopSubscriptionDraftDao;

@Component
public class RemoveUnprocessedSubscriptionsUseCase {

	@Autowired
	private ShopSubscriptionDraftDao shopSubscriptionDraftDao;

	@Transactional
	public void apply() {
		var timeDiff = Instant.now().minus(1, ChronoUnit.DAYS).toEpochMilli();

		shopSubscriptionDraftDao.deleteByCreatedAtLessThan(timeDiff);

	}

}
