package com.marketplace.domain.category.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryDao;

@Component
public class GenerateLftRgtUseCase {
	
	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private GetHierarchicalCategoryUseCase getHierarchicalCategoryUseCase;
	
	public void apply() {
		var list = getHierarchicalCategoryUseCase.apply(false);
		
		generate(list, 1);
		
		categoryDao.saveLftRgt(flatten(list));
	}
	
	private int generate(List<Category> list, int value) {
		var nextValue = value;
		
		for (var category : list) {
			category.setLft(nextValue);
			
			nextValue += 1;
			
			var children = category.getChildren();
			
			if (children != null && !children.isEmpty()) {
				nextValue = generate(children, nextValue);
			}
			
			category.setRgt(nextValue);
			
			nextValue += 1;
		}
		
		return nextValue;
	}
	
	private List<Category> flatten(List<Category> list) {
		var flattened = new ArrayList<Category>();
		for (var category : list) {
			
			var children = category.getChildren();
			
			category.setChildren(null);
			
			flattened.add(category);
			
			if (children != null && !children.isEmpty()) {
				var result = flatten(children);
				flattened.addAll(result);
			}
		}
		
		return flattened;
	}
	
}
