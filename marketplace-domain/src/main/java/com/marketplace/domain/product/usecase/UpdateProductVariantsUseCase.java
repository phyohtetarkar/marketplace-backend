package com.marketplace.domain.product.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.product.ProductCreateInput;
import com.marketplace.domain.product.dao.ProductDao;
import com.marketplace.domain.product.dao.ProductVariantDao;

@Component
public class UpdateProductVariantsUseCase {
	
	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductVariantDao variantDao;
	
	@Transactional
	public void apply(long shopId, long productId, List<ProductCreateInput.Variant> values) {
		if (!productDao.existsByIdAndShop(productId, shopId)) {
			throw new ApplicationException("Product not found");
		}
		
		var variants = Optional.ofNullable(values).orElseGet(ArrayList::new);
		
		boolean atLeastOneVarant = variants.stream().anyMatch(v -> v.isDeleted() == false);

		if (!atLeastOneVarant) {
			throw new ApplicationException("At least one variant required");
		}
		
		var price = variants.stream()
				.map(ProductCreateInput.Variant::getPrice)
				.sorted((f, s) -> s.compareTo(f))
				.findFirst().orElse(null);
		
		for (var variant : variants) {
			if (variant.isDeleted()) {
				variantDao.updateDeleted(variant.getId(), true);
				continue;
			}

			var variantAttributes = variant.getAttributes();

			if (variantAttributes == null || variantAttributes.isEmpty()) {
				throw new ApplicationException("Invalid variant attributes");
			}

			var pv = variantDao.save(productId, variant);

			if (variant.getId() != pv.getId()) {
				for (var va : variantAttributes) {
					if (!Utils.hasText(va.getAttribute())) {
						throw new ApplicationException("Required variant attribute name");
					}

					if (!Utils.hasText(va.getValue())) {
						throw new ApplicationException("Required variant attribute value");
					}

				}

				variantDao.createVariantAttributes(productId, pv.getId(), variantAttributes);

			}
		}
		
		productDao.updatePrice(productId, price);
	}

}
