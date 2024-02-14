package com.marketplace.domain.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SearchCriteria;
import com.marketplace.domain.common.SearchCriteria.Operator;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.UserQuery;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class GetAllUserUseCase {

	@Autowired
	private UserDao dao;

	public PageData<User> apply(UserQuery query) {
		var sq = new SearchQuery();
		
		if (Utils.hasText(query.getEmail())) {
			var c = SearchCriteria.simple("email", Operator.EQUAL, query.getEmail());
			sq.addCriteria(c);
		}

		if (Utils.hasText(query.getPhone())) {
			var c = SearchCriteria.simple("phone", Operator.EQUAL, query.getPhone());
			sq.addCriteria(c);
		}

		if (query.getStaffOnly() == Boolean.TRUE) {
			var c = SearchCriteria.simple("role", Operator.NOT_EQ, User.Role.USER);
			sq.addCriteria(c);
		}

		if (query.getVerified() != null) {
			var c = SearchCriteria.simple("verified", Operator.EQUAL, query.getVerified().toString().toUpperCase());
			sq.addCriteria(c);
		}

		if (StringUtils.hasText(query.getName())) {
			var name = query.getName().toLowerCase();
			var c = SearchCriteria.simple("name", Operator.LIKE, name);
			sq.addCriteria(c);
		}
		
		sq.setPageQuery(PageQuery.of(query.getPage(), Constants.PAGE_SIZE));

		return dao.findAll(sq);
	}

}
