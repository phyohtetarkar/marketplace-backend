package com.shoppingcenter.data.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.FavoriteProduct;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;

@Repository
public class FavoriteProductDaoImpl implements FavoriteProductDao {

    @Autowired
    private FavoriteProductRepo favoriteProductRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public void add(String userId, long productId) {
        var entity = new FavoriteProductEntity();
        entity.setProduct(productRepo.getReferenceById(productId));
        entity.setUser(userRepo.getReferenceById(userId));
        favoriteProductRepo.save(entity);
    }

    @Override
    public void delete(long id) {
        favoriteProductRepo.deleteById(id);
    }

    @Override
    public void deleteByProduct(long productId) {
        favoriteProductRepo.deleteByProduct_Id(productId);
    }

    @Override
    public boolean existsByUserAndProduct(String userId, long productId) {
        return favoriteProductRepo.existsByUser_IdAndProduct_Id(userId, productId);
    }

    @Override
    public PageData<FavoriteProduct> findByUser(String userId, int page) {
        var sort = Sort.by(Order.desc("createdAt"));
        var request = PageRequest.of(page, Constants.PAGE_SIZE, sort);
        var pageResult = favoriteProductRepo.findByUser_Id(userId, request);
        return PageDataMapper.map(pageResult, e -> {
            var product = ProductMapper.toDomainCompat(e.getProduct(), imageUrl);
            return new FavoriteProduct(e.getId(), product);
        });
    }

}
