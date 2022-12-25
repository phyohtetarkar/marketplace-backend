package com.shoppingcenter.core.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shoppingcenter.core.PageResult;
import com.shoppingcenter.core.product.model.Product;
import com.shoppingcenter.data.product.FavoriteProductEntity;
import com.shoppingcenter.data.product.FavoriteProductRepo;

@Service
public class FavoriteProductServiceImpl implements FavoriteProductService {

    @Autowired
    private FavoriteProductRepo repo;

    @Value("${app.image.base-url}")
    private String baseUrl;

    @Override
    public void add(String userId, long productId) {
        FavoriteProductEntity entity = new FavoriteProductEntity();
        entity.getId().setUserId(userId);
        entity.getId().setProductId(productId);
        repo.save(entity);
    }

    @Override
    public void remove(String userId, long productId) {
        FavoriteProductEntity.Id id = new FavoriteProductEntity.Id();
        id.setUserId(userId);
        id.setProductId(productId);
        repo.deleteById(id);
    }

    @Override
    public PageResult<Product> findByUser(String userId, int page) {
        Page<FavoriteProductEntity> pageData = repo.findByUserId(userId);
        PageResult<Product> result = new PageResult<>();
        result.setContents(
                pageData.map(e -> Product.createCompat(e.getProduct(), baseUrl)).getContent());
        result.setCurrentPage(pageData.getNumber());
        result.setTotalPage(pageData.getTotalPages());
        result.setPageSize(pageData.getNumberOfElements());
        return result;
    }
}
