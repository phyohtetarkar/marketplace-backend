package com.marketplace.data;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.marketplace.domain.common.SearchCriteria;
import com.marketplace.domain.common.SearchCriteria.Operator;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class JpaSpecification<T> implements Specification<T> {

	private static final long serialVersionUID = 1L;

	private SearchCriteria criteria;

	public JpaSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (criteria.getOperator() == Operator.EQUAL) {
			if (StringUtils.hasText(criteria.getJoinPath())) {
				Join<T, ?> join = root.join(criteria.getJoinPath());
				return builder.equal(getPath(join, criteria.getKey()), criteria.getValue());
			}
			return builder.equal(getPath(root, criteria.getKey()), criteria.getValue());
		}

		if (criteria.getOperator() == Operator.EQUAL_IGNORE_CASE) {
			if (StringUtils.hasText(criteria.getJoinPath())) {
				Join<T, ?> join = root.join(criteria.getJoinPath());
				return builder.equal(builder.lower(getPath(join, criteria.getKey())), criteria.getValue());
			}
			return builder.equal(builder.lower(getPath(root, criteria.getKey())), criteria.getValue());
		}

		if (criteria.getOperator() == Operator.GREATER_THAN_EQ) {
			if (StringUtils.hasText(criteria.getJoinPath())) {
				Join<T, ?> join = root.join(criteria.getJoinPath());
				return builder.greaterThanOrEqualTo(getPath(join, criteria.getKey()), criteria.getValue().toString());
			}
			return builder.greaterThanOrEqualTo(getPath(root, criteria.getKey()), criteria.getValue().toString());
		}

		if (criteria.getOperator() == Operator.GREATER_THAN) {
			if (StringUtils.hasText(criteria.getJoinPath())) {
				Join<T, ?> join = root.join(criteria.getJoinPath());
				return builder.greaterThan(getPath(join, criteria.getKey()), criteria.getValue().toString());
			}
			return builder.greaterThan(getPath(root, criteria.getKey()), criteria.getValue().toString());
		}

		if (criteria.getOperator() == Operator.LESS_THAN_EQ) {
			if (StringUtils.hasText(criteria.getJoinPath())) {
				Join<T, ?> join = root.join(criteria.getJoinPath());
				return builder.lessThanOrEqualTo(getPath(join, criteria.getKey()), criteria.getValue().toString());
			}
			return builder.lessThanOrEqualTo(getPath(root, criteria.getKey()), criteria.getValue().toString());
		}

		if (criteria.getOperator() == Operator.LESS_THAN) {
			if (StringUtils.hasText(criteria.getJoinPath())) {
				Join<T, ?> join = root.join(criteria.getJoinPath());
				return builder.lessThan(getPath(join, criteria.getKey()), criteria.getValue().toString());
			}
			return builder.lessThan(getPath(root, criteria.getKey()), criteria.getValue().toString());
		}

		if (criteria.getOperator() == Operator.LIKE) {
			return builder.like(builder.lower(getPath(root, criteria.getKey())), criteria.getValue().toString());
		}

		if (criteria.getOperator() == Operator.NOT_EQ) {
			if (StringUtils.hasText(criteria.getJoinPath())) {
				Join<T, ?> join = root.join(criteria.getJoinPath());
				return builder.notEqual(getPath(join, criteria.getKey()), criteria.getValue());
			}
			return builder.notEqual(getPath(root, criteria.getKey()), criteria.getValue());
		}

		if (criteria.getOperator() == Operator.NOT_NULL) {
			if (StringUtils.hasText(criteria.getJoinPath())) {
				Join<T, ?> join = root.join(criteria.getJoinPath());
				return getPath(join, criteria.getKey()).isNotNull();
			}
			return getPath(root, criteria.getKey()).isNotNull();
		}

		if (criteria.getOperator() == Operator.NULL) {
			if (StringUtils.hasText(criteria.getJoinPath())) {
				Join<T, ?> join = root.join(criteria.getJoinPath());
				return getPath(join, criteria.getKey()).isNull();
			}
			return getPath(root, criteria.getKey()).isNull();
		}

		if (criteria.getOperator() == Operator.IN) {
			if (StringUtils.hasText(criteria.getJoinPath())) {
				Join<T, ?> join = root.join(criteria.getJoinPath());

				return getPath(join, criteria.getKey()).in(criteria.getValues());
			}
			return getPath(root, criteria.getKey()).in(criteria.getValues());
		}
		return null;
	}

	private <Y> Expression<Y> getPath(Root<T> root, String key) {
		var keys = key.split("\\.");

		Path<Y> path = root.get(keys[0]);

		var len = keys.length;

		if (len == 1) {
			return path;
		}

		for (int i = 1; i < len; i++) {
			path = path.get(keys[i]);
		}

		return path;
	}

	private <Y> Expression<Y> getPath(Join<T, ?> join, String key) {
		var keys = key.split("\\.");

		Path<Y> path = join.get(keys[0]);

		var len = keys.length;

		if (len == 1) {
			return path;
		}

		for (int i = 1; i < len; i++) {
			path = path.get(keys[i]);
		}

		return path;
	}

}
