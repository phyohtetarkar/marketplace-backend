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
    public long save(ProductImage image) {
        var entity = imageRepo.findById(image.getId()).orElseGet(ProductImageEntity::new);
        entity.setName(image.getName());
        entity.setSize(image.getSize());
        entity.setThumbnail(image.isThumbnail());
        entity.setProduct(productRepo.getReferenceById(image.getProductId()));

        var result = imageRepo.save(entity);

        return result.getId();
    }

    @Override
    public void delete(long id) {
        imageRepo.deleteById(id);
    }

    @Override
    public List<ProductImage> findByProduct(long productId) {
        return imageRepo.findByProduct_Id(productId).stream()
                .map(e -> ProductMapper.toImage(e, imageUrl))
                .collect(Collectors.toList());
    }

}
