package com.shoppingcenter.domain.product.usecase;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetProductBrandByNameLikeUseCase {

	 private ProductDao dao;

	public GetProductBrandByNameLikeUseCase(ProductDao dao) {
		super();
		this.dao = dao;
	}
	 
	public List<String> apply(String q) {
		if (!Utils.hasText(q)) {
			return new ArrayList<String>();
		}
		
		return dao.findProductBrandsByQuery("%" + q + "%");
	}
}
