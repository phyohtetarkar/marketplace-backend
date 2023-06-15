package com.shoppingcenter.data.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;

@Repository
public class ProductVariantDaoImpl implements ProductVariantDao {

	@Autowired
	private ProductVariantRepo variantRepo;

	@Autowired
	private ProductRepo productRepo;

	@Override
	public void saveAll(List<ProductVariant> list) {
		var entities = list.stream().map(variant -> {
			var entity = variantRepo.findById(variant.getId()).orElseGet(ProductVariantEntity::new);
			entity.setPrice(variant.getPrice());
			entity.setSku(variant.getSku());
			entity.setStockLeft(variant.getStockLeft());
			if (variant.getAttributes() != null) {
				entity.setAttributes(variant.getAttributes().stream().map(a -> {
					var en = new ProductVariantAttributeEntity();
					en.setAttributeId(a.getAttributeId());
					en.setAttribute(a.getAttribute());
					en.setValue(a.getValue());
					en.setSort(a.getSort());
					return en;
				}).collect(Collectors.toSet()));
			}
			entity.setProduct(productRepo.getReferenceById(variant.getProductId()));
			return entity;
		}).toList();
		variantRepo.saveAll(entities);
	}

	@Override
	public void updateStockLeft(long id, int stockLeft) {
		var entity = variantRepo.getReferenceById(id);
		entity.setStockLeft(stockLeft);
	}

	@Override
	public void deleteAll(List<Long> list) {
		variantRepo.deleteAllById(list);
	}

	@Override
	public boolean exists(long id) {
		return variantRepo.existsById(id);
	}
	
	@Override
	public ProductVariant findById(long id) {
		return variantRepo.findById(id).map(ProductMapper::toVariant).orElse(null);
	}

}
