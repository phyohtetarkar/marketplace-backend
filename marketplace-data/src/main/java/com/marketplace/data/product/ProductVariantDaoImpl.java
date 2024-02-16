package com.marketplace.data.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.product.ProductCreateInput;
import com.marketplace.domain.product.ProductVariant;
import com.marketplace.domain.product.dao.ProductVariantDao;

@Repository
public class ProductVariantDaoImpl implements ProductVariantDao {

	@Autowired
	private ProductVariantRepo variantRepo;

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductAttributeRepo productAttributeRepo;
	
	@Autowired
	private ProductVariantAttributeRepo productVariantAttributeRepo;
	
	@Override
	public ProductVariant save(long productId, ProductCreateInput.Variant values) {
		var entity = variantRepo.findById(values.getId()).orElseGet(ProductVariantEntity::new);
		entity.setPrice(values.getPrice());
		entity.setSku(values.getSku());
		entity.setAvailable(values.isAvailable());
		entity.setProduct(productRepo.getReferenceById(productId));
		var result = variantRepo.save(entity);
		return ProductMapper.toVariant(result);
	}
	
	@Override
	public void createProductAttributes(long productId, List<ProductCreateInput.Attribute> list) {
		var product = productRepo.getReferenceById(productId);
		var attributes = list.stream().map(a -> {
			var en = new ProductAttributeEntity();
			en.getId().setName(a.getName());
			en.setSort(a.getSort());
			en.setProduct(product);
			return en;
		}).toList();
		
		productAttributeRepo.saveAll(attributes);
	}
	
	@Override
	public void createVariantAttributes(long productId, long variantId, List<ProductCreateInput.VariantAttribute> list) {
		var variant = variantRepo.getReferenceById(variantId);
		var attributes = list.stream().map(a -> {
			var en = new ProductVariantAttributeEntity();
			en.getId().setAttributeId(new ProductAttributeEntity.ID(productId, a.getAttribute()));
			en.setValue(a.getValue());
			en.setSort(a.getSort());
			en.setVSort(a.getVSort());
			en.setVariant(variant);
			return en;
		}).toList();
		
		productVariantAttributeRepo.saveAll(attributes);
	}
	
	@Override
	public void updateDeleted(long variantId, boolean deleted) {
		variantRepo.updateDeleted(variantId, deleted);
	}

	@Override
	public void deleteAll(List<Long> list) {
		variantRepo.deleteAllById(list);
	}

	@Override
	public boolean existsById(long id) {
		return variantRepo.existsById(id);
	}
	
	@Override
	public ProductVariant findById(long id) {
		return variantRepo.findById(id).map(ProductMapper::toVariant).orElse(null);
	}

}
