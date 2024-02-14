package com.marketplace.domain.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchQuery {

	private List<SearchCriteria> criterias;
	
	private PageQuery pageQuery;
	
	public SearchQuery() {
		this.criterias = new ArrayList<SearchCriteria>();
	}
	
	public void addCriteria(SearchCriteria criteria) {
		if (criterias != null) {
			criterias.add(criteria);
		}
	}
	
}
