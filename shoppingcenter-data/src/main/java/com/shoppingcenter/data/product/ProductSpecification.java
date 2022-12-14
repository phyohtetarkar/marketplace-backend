package com.shoppingcenter.data.product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification implements Specification<ProductEntity> {

	private static final long serialVersionUID = 1L;

	@Override
	public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return null;
	}

}
