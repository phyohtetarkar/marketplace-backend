package com.shoppingcenter.data.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;

@Repository
public class FavoriteProductDaoImpl implements FavoriteProductDao {

    @Autowired
    private FavoriteProductRepo favoriteProductRepo;
    @Override
    public void add(long userId, long productId) {
        var entity = new FavoriteProductEntity();
        var id = new FavoriteProductEntity.ID(productId, userId);
        entity.setId(id);
        favoriteProductRepo.save(entity);
    }

    @Override
    public void delete(long userId, long productId) {
        var id = new FavoriteProductEntity.ID(productId, userId);
        favoriteProductRepo.deleteById(id);
    }

    @Override
    public void deleteByProduct(long productId) {
        favoriteProductRepo.deleteByProductId(productId);
    }

    @Override
    public boolean exists(long userId, long productId) {
        var id = new FavoriteProductEntity.ID(productId, userId);
        return favoriteProductRepo.existsById(id);
    }

    @Override
    public PageData<Product> findByUser(long userId, int page) {
        var sort = Sort.by(Order.desc("createdAt"));
        var request = PageRequest.of(page, Constants.PAGE_SIZE, sort);
        var pageResult = favoriteProductRepo.findByUserId(userId, request);
        return PageDataMapper.map(pageResult, e -> ProductMapper.toDomainCompat(e.getProduct()));
    }

}
