package com.shoppingcenter.data;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.SearchCriteria.Operator;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class BasicSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = 1L;
    
	private SearchCriteria criteria;

    public BasicSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperator() == Operator.EQUAL) {
            if (StringUtils.hasText(criteria.getJoinPath())) {
                Join<T, ?> join = root.join(criteria.getJoinPath());
                return builder.equal(join.get(criteria.getKey()), criteria.getValue());
            }
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }

        if (criteria.getOperator() == Operator.EQUAL_IGNORE_CASE) {
        	if (StringUtils.hasText(criteria.getJoinPath())) {
                Join<T, ?> join = root.join(criteria.getJoinPath());
                return builder.equal(builder.lower(join.get(criteria.getKey())), criteria.getValue());
            }
            return builder.equal(builder.lower(root.get(criteria.getKey())), criteria.getValue());
        }

        if (criteria.getOperator() == Operator.GREATER_THAN_EQ) {
        	if (StringUtils.hasText(criteria.getJoinPath())) {
                Join<T, ?> join = root.join(criteria.getJoinPath());
                return builder.greaterThanOrEqualTo(join.get(criteria.getKey()), criteria.getValue().toString());
            }
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
        }
        
        if (criteria.getOperator() == Operator.GREATER_THAN) {
        	if (StringUtils.hasText(criteria.getJoinPath())) {
        		Join<T, ?> join = root.join(criteria.getJoinPath());
        		return builder.greaterThan(join.get(criteria.getKey()), criteria.getValue().toString());
        	}
        	return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
        }

        if (criteria.getOperator() == Operator.LESS_THAN_EQ) {
        	if (StringUtils.hasText(criteria.getJoinPath())) {
                Join<T, ?> join = root.join(criteria.getJoinPath());
                return builder.lessThanOrEqualTo(join.get(criteria.getKey()), criteria.getValue().toString());
            }
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
        }
        
        if (criteria.getOperator() == Operator.LESS_THAN) {
        	if (StringUtils.hasText(criteria.getJoinPath())) {
        		Join<T, ?> join = root.join(criteria.getJoinPath());
        		return builder.lessThan(join.get(criteria.getKey()), criteria.getValue().toString());
        	}
        	return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
        }

        if (criteria.getOperator() == Operator.LIKE) {
            return builder.like(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString());
        }

        if (criteria.getOperator() == Operator.NOT_EQ) {
        	if (StringUtils.hasText(criteria.getJoinPath())) {
                Join<T, ?> join = root.join(criteria.getJoinPath());
                return builder.notEqual(builder.lower(join.get(criteria.getKey())), criteria.getValue());
            }
            return builder.notEqual(builder.lower(root.get(criteria.getKey())), criteria.getValue());
        }

        if (criteria.getOperator() == Operator.IN) {
            if (StringUtils.hasText(criteria.getJoinPath())) {
                Join<T, ?> join = root.join(criteria.getJoinPath());

                return join.in(criteria.getValues());
            }
            return root.get(criteria.getKey()).in(criteria.getValues());
        }
        return null;
    }

}
