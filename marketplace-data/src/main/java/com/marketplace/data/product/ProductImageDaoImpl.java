package com.marketplace.data.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.product.view.ProductImageNameView;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.product.ProductCreateInput;
import com.marketplace.domain.product.ProductImage;
import com.marketplace.domain.product.dao.ProductImageDao;

@Repository
public class ProductImageDaoImpl implements ProductImageDao {

	@Autowired
	private ProductImageRepo imageRepo;

	@Autowired
	private ProductRepo productRepo;

	@Override
	public ProductImage create(long productId, ProductCreateInput.Image values) {
		var entity = new ProductImageEntity();
		entity.setThumbnail(values.isThumbnail());
		entity.setSize(values.getSize());
		entity.setName(values.getName());
		entity.setProduct(productRepo.getReferenceById(productId));

		var result = imageRepo.save(entity);
		return ProductMapper.toImage(result);
	}

	@Override
	public ProductImage update(ProductCreateInput.Image values) {
		var entity = imageRepo.findById(values.getId()).orElseThrow(() -> new ApplicationException("Image not found"));
		entity.setThumbnail(values.isThumbnail());
		var result = imageRepo.save(entity);
		return ProductMapper.toImage(result);
	}

	@Override
	public void saveAll(long productId, List<ProductCreateInput.Image> list) {
		imageRepo.saveAll(list.stream().map(image -> {
			var entity = imageRepo.findById(image.getId()).orElseGet(ProductImageEntity::new);
			entity.setThumbnail(image.isThumbnail());
			if (entity.getId() == 0) {
				entity.setSize(image.getSize());
				entity.setName(image.getName());
				entity.setProduct(productRepo.getReferenceById(productId));
			}

			return entity;
		}).toList());
	}

	@Override
	public void deleteAll(List<Long> list) {
		imageRepo.deleteAllById(list);
	}

	@Override
	public String getImageName(long id) {
		return imageRepo.getProductImageById(id, ProductImageNameView.class)
				.map(ProductImageNameView::getName)
				.orElse("");
	}

}
