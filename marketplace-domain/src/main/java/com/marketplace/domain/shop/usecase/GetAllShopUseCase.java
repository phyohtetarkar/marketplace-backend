package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SearchCriteria;
import com.marketplace.domain.common.SearchCriteria.Operator;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopQuery;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class GetAllShopUseCase {

	@Autowired
	private ShopDao dao;

	@Transactional(readOnly = true)
	public PageData<Shop> apply(ShopQuery query) {
		var sq = new SearchQuery();
		if (Utils.hasText(query.getQ())) {
			var q = "%" + query.getQ().toLowerCase() + "%";

			var c = SearchCriteria.simple("name", Operator.LIKE, q);
			c.setOrCriteria(SearchCriteria.simple("headline", Operator.LIKE, q));

			sq.addCriteria(c);
		}

		if (query.getCityId() != null && query.getCityId() > 0) {
			var c = SearchCriteria.simple("city.id", Operator.EQUAL, query.getCityId());
			sq.addCriteria(c);
		}

		if (query.getFeatured() == Boolean.TRUE) {
			var c = SearchCriteria.simple("featured", Operator.EQUAL, Boolean.TRUE);
			sq.addCriteria(c);
		}

		if (query.getExpired() != null) {
			var operator = query.getExpired() ? Operator.LESS_THAN : Operator.GREATER_THAN_EQ;

			var c = SearchCriteria.simple("expiredAt", operator, System.currentTimeMillis());
			sq.addCriteria(c);
		}

		if (query.getStatus() != null) {
			var c = SearchCriteria.simple("status", Operator.EQUAL, query.getStatus());
			sq.addCriteria(c);
		}

		var deletedCriteria = SearchCriteria.simple("deleted", Operator.EQUAL, Boolean.FALSE);

		sq.addCriteria(deletedCriteria);

		sq.setPageQuery(PageQuery.of(query.getPage(), Constants.PAGE_SIZE));

		return dao.findAll(sq);
	}

}
