package com.marketplace.domain.order.usecase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SearchCriteria;
import com.marketplace.domain.common.SearchCriteria.Operator;
import com.marketplace.domain.order.Order;
import com.marketplace.domain.order.OrderQuery;
import com.marketplace.domain.order.dao.OrderDao;

@Component
public class GetAllOrderByQueryUseCase {

	@Autowired
	private OrderDao dao;

	public PageData<Order> apply(OrderQuery query) {
		var sq = new SearchQuery();

		if (query.getShopId() != null && query.getShopId() > 0) {
			var c = SearchCriteria.simple("shop.id", Operator.EQUAL, query.getShopId());
			sq.addCriteria(c);
		}

		if (query.getUserId() != null && query.getUserId() > 0) {
			var c = SearchCriteria.simple("customer.id", Operator.EQUAL, query.getUserId());
			sq.addCriteria(c);
		}

		if (query.getStatus() != null) {
			var c = SearchCriteria.simple("status", Operator.EQUAL, query.getStatus());
			sq.addCriteria(c);
		}

		if (StringUtils.hasText(query.getCode())) {
			var c = SearchCriteria.simple("orderCode", Operator.EQUAL, query.getCode());
			sq.addCriteria(c);
		}

		if (StringUtils.hasText(query.getDate())) {
			var date = LocalDate.parse(query.getDate());
			ZoneId zoneId = ZoneOffset.UTC;

			try {
				zoneId = StringUtils.hasText(query.getTimeZone()) ? ZoneId.of(query.getTimeZone()) : ZoneOffset.UTC;
			} catch (Exception e) {
			}

			var from = date.atStartOfDay(zoneId).toInstant().toEpochMilli();
			var to = date.atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli();
			
			var cFrom = SearchCriteria.simple("createdAt", Operator.GREATER_THAN_EQ, from);
			sq.addCriteria(cFrom);
			
			var cTo = SearchCriteria.simple("createdAt", Operator.LESS_THAN_EQ, to);
			sq.addCriteria(cTo);
		}
		
		sq.setPageQuery(PageQuery.of(query.getPage(), Constants.PAGE_SIZE));

		return dao.findAll(sq);
	}

}
