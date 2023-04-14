package com.shoppingcenter.domain.product.usecase;

import java.util.stream.Collectors;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductImageDao;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

import lombok.Setter;

@Setter
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {

    private ProductDao productDao;

    private ProductImageDao imageDao;

    private FavoriteProductDao favoriteProductDao;

    private CartItemDao cartItemDao;

    private FileStorageAdapter fileStorageAdapter;

    @Override
    public void apply(long id) {

        var product = productDao.findById(id);

        if (product == null) {
            throw new ApplicationException("Product not found");
        }

        favoriteProductDao.deleteByProduct(id);

        cartItemDao.deleteByProduct(id);

        var images = imageDao.findByProduct(id).stream().map(ProductImage::getName).collect(Collectors.toList());

        productDao.delete(id);

        String dir = Constants.IMG_PRODUCT_ROOT;

        fileStorageAdapter.delete(dir, images);
    }

}
