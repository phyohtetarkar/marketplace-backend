package com.shoppingcenter.data.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.dao.ProductImageDao;

@Repository
public class ProductImageDaoImpl implements ProductImageDao {

    @Autowired
    private ProductImageRepo imageRepo;

    @Autowired
    private ProductRepo productRepo;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public void save(ProductImage image) {
        var entity = imageRepo.findById(image.getId()).orElseGet(ProductImageEntity::new);
        entity.setName(image.getName());
        entity.setSize(image.getSize());
        entity.setThumbnail(image.isThumbnail());
        if (entity.getId() == 0) {
            entity.setProduct(productRepo.getReferenceById(image.getProductId()));
        }

        imageRepo.save(entity);

    }

    @Override
    public void saveAll(List<ProductImage> list) {
        imageRepo.saveAll(list.stream().map(image -> {
            var entity = imageRepo.findById(image.getId()).orElseGet(ProductImageEntity::new);
            entity.setName(image.getName());
            entity.setSize(image.getSize());
            entity.setThumbnail(image.isThumbnail());
            if (entity.getId() == 0) {
                entity.setProduct(productRepo.getReferenceById(image.getProductId()));
            }

            return entity;
        }).toList());
    }

    @Override
    public void delete(ProductImage image) {
        imageRepo.deleteById(image.getId());
    }

    @Override
    public void deleteAll(List<ProductImage> list) {
        imageRepo.deleteAllById(list.stream().map(image -> {
            return image.getId();
        }).toList());
    }

    @Override
    public List<ProductImage> findByProduct(long productId) {
        return imageRepo.findByProductId(productId).stream()
                .map(e -> ProductMapper.toImage(e, imageUrl))
                .collect(Collectors.toList());
    }

}
