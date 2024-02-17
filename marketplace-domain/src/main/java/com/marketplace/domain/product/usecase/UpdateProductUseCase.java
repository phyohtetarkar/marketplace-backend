package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.category.CategoryDao;
import com.marketplace.domain.discount.DiscountDao;
import com.marketplace.domain.product.ProductUpdateInput;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class UpdateProductUseCase {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private DiscountDao discountDao;

	@Transactional
	public void apply(ProductUpdateInput values) {
		
		if (!productDao.existsByIdAndShop(values.getId(), values.getShopId())) {
			throw new ApplicationException("Product not found");
		}

		if (!Utils.hasText(values.getName())) {
			throw new ApplicationException("Required product name");
		}

		var rawSlug = Utils.convertToSlug(values.getSlug());

		if (!Utils.hasText(rawSlug)) {
			throw new ApplicationException("Required product slug");
		}

		if (!categoryDao.existsById(values.getCategoryId())) {
			throw new ApplicationException("Category not found");
		}

		if (values.getDiscountId() != null && !discountDao.existsById(values.getDiscountId())) {
			throw new ApplicationException("Discount not found");
		}

		var slug = Utils.generateSlug(rawSlug, v -> productDao.existsByIdNotAndSlug(values.getId(), v));
		values.setSlug(slug);

		if (values.getPrice() == null) {
			throw new ApplicationException("Required product price");
		}

		productDao.update(values);
	}

}
