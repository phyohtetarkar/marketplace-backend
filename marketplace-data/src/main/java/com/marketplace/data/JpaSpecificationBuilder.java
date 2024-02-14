package com.marketplace.data;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.marketplace.domain.common.SearchCriteria;

public class JpaSpecificationBuilder {

	public static <T> Specification<T> build(List<SearchCriteria> criterias, Class<T> clazz) {
		Specification<T> spec = null;
		
		for (SearchCriteria criteria : criterias) {
			Specification<T> jpaSpec = new JpaSpecification<T>(criteria);
			var orCriteria = criteria.getOrCriteria();
			
			while (orCriteria != null) {
				jpaSpec = jpaSpec.or(new JpaSpecification<T>(orCriteria));
				orCriteria = orCriteria.getOrCriteria();
			}
			
			spec = spec != null ? spec.and(jpaSpec) : Specification.where(jpaSpec);
		}
		
		return spec;
	}
	
}
