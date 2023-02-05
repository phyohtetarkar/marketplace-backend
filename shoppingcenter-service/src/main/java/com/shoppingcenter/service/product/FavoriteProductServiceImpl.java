package com.shoppingcenter.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.product.FavoriteProductEntity;
import com.shoppingcenter.data.product.FavoriteProductRepo;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.Constants;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.product.model.Product;

@Service
public class FavoriteProductServiceImpl implements FavoriteProductService {

    @Autowired
    private FavoriteProductRepo repo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Value("${app.image.base-url}")
    private String baseUrl;

    @Override
    public void add(String userId, long productId) {

        if (!StringUtils.hasText(userId) || !userRepo.existsByIdAndDisabledFalse(userId)) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "User not found");
        }

        if (!productRepo.existsByIdAndStatus(productId, Product.Status.PUBLISHED.name())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "Product not found");
        }

        FavoriteProductEntity entity = new FavoriteProductEntity();
        entity.setProduct(productRepo.getReferenceById(productId));
        entity.setUser(userRepo.getReferenceById(userId));

        repo.save(entity);
    }

    @Override
    public void remove(long id) {
        repo.deleteById(id);
    }

    @Override
    public PageData<Product> findByUser(String userId, Integer page) {
        Sort sort = Sort.by(Order.desc("createdAt"));
        PageRequest request = PageRequest.of(page != null && page > 0 ? page : 1, Constants.PAGE_SIZE, sort);
        Page<FavoriteProductEntity> pageResult = repo.findByUser_Id(userId, request);

        return PageData.build(pageResult, e -> Product.createCompat(e.getProduct(), baseUrl));
    }
}
