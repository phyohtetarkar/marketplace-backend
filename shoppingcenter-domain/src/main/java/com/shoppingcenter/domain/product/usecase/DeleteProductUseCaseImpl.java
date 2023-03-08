package com.shoppingcenter.domain.product.usecase;

import java.util.stream.Collectors;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductImageDao;
import com.shoppingcenter.domain.shop.usecase.ValidateShopActiveUseCase;
import com.shoppingcenter.domain.shop.usecase.ValidateShopMemberUseCase;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

import lombok.Setter;

@Setter
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {

    private ProductDao productDao;

    private ProductImageDao imageDao;

    private FavoriteProductDao favoriteProductDao;

    private CartItemDao cartItemDao;

    private FileStorageAdapter fileStorageAdapter;

    private ValidateShopActiveUseCase validateShopActiveUseCase;

    private ValidateShopMemberUseCase validateShopMemberUseCase;

    private AuthenticationContext authenticationContext;

    @Override
    public void apply(long id) {

        var product = productDao.findById(id);

        if (product == null) {
            throw new ApplicationException("Product not found");
        }

        validateShopMemberUseCase.apply(product.getShopId(), authenticationContext.getUserId());

        validateShopActiveUseCase.apply(product.getShopId());

        favoriteProductDao.deleteByProduct(id);

        cartItemDao.deleteByProduct(id);

        var images = imageDao.findByProduct(id).stream().map(ProductImage::getName).collect(Collectors.toList());

        productDao.delete(id);

        String dir = "product";

        fileStorageAdapter.delete(dir, images);
    }

}
