package com.marketplace.data.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.data.user.UserRepo;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.dao.FavoriteProductDao;

@Repository
public class FavoriteProductDaoImpl implements FavoriteProductDao {

    @Autowired
    private FavoriteProductRepo favoriteProductRepo;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private ProductRepo productRepo;
    
    @Override
    public void add(long userId, long productId) {
        var entity = new FavoriteProductEntity();
        entity.setUser(userRepo.getReferenceById(userId));
        entity.setProduct(productRepo.getReferenceById(productId));
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
    public long getFavoriteCountByUser(long userId) {
    	return favoriteProductRepo.countByUserId(userId);
    }

    @Override
    public PageData<Product> findByUser(long userId, PageQuery pageQuery) {
        var request = PageQueryMapper.fromPageQuery(pageQuery);
        var pageResult = favoriteProductRepo.findByUserId(userId, request);
        return PageDataMapper.map(pageResult, e -> ProductMapper.toDomainCompat(e.getProduct()));
    }

}
