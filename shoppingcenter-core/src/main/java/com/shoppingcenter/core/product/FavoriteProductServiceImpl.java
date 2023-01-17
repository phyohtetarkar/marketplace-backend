package com.shoppingcenter.core.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.Constants;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.product.model.Product;
import com.shoppingcenter.data.product.FavoriteProductEntity;
import com.shoppingcenter.data.product.FavoriteProductRepo;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.user.UserRepo;

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

        if (!StringUtils.hasText(userId) || !userRepo.existsById(userId)) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "User not found");
        }

        if (!productRepo.existsById(productId)) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "Product not found");
        }

        FavoriteProductEntity entity = new FavoriteProductEntity();
        entity.setProduct(productRepo.getReferenceById(productId));
        entity.setUser(userRepo.getReferenceById(userId));

        repo.save(entity);
    }

    @Override
    public void remove(String userId, long productId) {
        if (!StringUtils.hasText(userId) || !userRepo.existsById(userId)) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "User not found");
        }

        if (!productRepo.existsById(productId)) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "Product not found");
        }

        repo.deleteById(String.format("%d:%s", productId, userId));
    }

    @Override
    public PageData<Product> findByUser(String userId, Integer page) {
        Sort sort = Sort.by(Order.desc("createdAt"));
        PageRequest request = PageRequest.of(page != null && page > 0 ? page : 1, Constants.PAGE_SIZE, sort);
        Page<FavoriteProductEntity> pageResult = repo.findByUser_Id(userId, request);

        return PageData.build(pageResult, e -> Product.createCompat(e.getProduct(), baseUrl));
    }
}
