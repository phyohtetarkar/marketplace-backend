package com.shoppingcenter.core.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.shoppingcenter.core.Constants;
import com.shoppingcenter.core.PageData;
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
    public PageData<Product> findByUser(String userId, Integer page) {
        Sort sort = Sort.by(Order.desc("createdAt"));
        PageRequest request = PageRequest.of(page != null && page > 0 ? page : 1, Constants.PAGE_SIZE, sort);
        Page<FavoriteProductEntity> pageResult = repo.findByUserId(userId, request);
        PageData<Product> data = new PageData<>();
        data.setContents(
                pageResult.map(e -> Product.createCompat(e.getProduct(), baseUrl)).getContent());
        data.setCurrentPage(pageResult.getNumber());
        data.setTotalPage(pageResult.getTotalPages());
        data.setPageSize(pageResult.getNumberOfElements());
        return data;
    }
}
