package com.shoppingcenter.domain.product.usecase;

import java.io.File;
import java.util.stream.Collectors;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

import lombok.Setter;

@Setter
public class DeleteProductUseCase {

    private ProductDao productDao;

    private FavoriteProductDao favoriteProductDao;

    private CartItemDao cartItemDao;

    private FileStorageAdapter fileStorageAdapter;

    public void apply(long id) {

        var product = productDao.findById(id);

        if (product == null) {
            throw new ApplicationException("Product not found");
        }

        favoriteProductDao.deleteByProduct(id);

        cartItemDao.deleteByProduct(id);

        var images = product.getImages().stream().map(ProductImage::getName).collect(Collectors.toList());

        productDao.delete(id);

        var dir = Constants.IMG_SHOP_ROOT + File.separator + product.getShop().getId() + File.separator + Constants.IMG_PRODUCT_ROOT;

        fileStorageAdapter.delete(dir, images);
    }

}
