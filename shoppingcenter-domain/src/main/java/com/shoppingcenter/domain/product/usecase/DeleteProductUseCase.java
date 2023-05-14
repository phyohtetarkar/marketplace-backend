package com.shoppingcenter.domain.product.usecase;

import java.util.stream.Collectors;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.order.dao.OrderItemDao;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

import lombok.Setter;

@Setter
public class DeleteProductUseCase {

    private ProductDao productDao;

    private FavoriteProductDao favoriteProductDao;

    private CartItemDao cartItemDao;
    
    private OrderItemDao orderItemDao;
    
    private ShopMemberDao shopMemberDao;

    private FileStorageAdapter fileStorageAdapter;
    
    public void apply(long userId, long productId) {

        var product = productDao.findById(productId);

        if (product == null) {
            throw new ApplicationException("Product not found");
        }
        
        if (!shopMemberDao.existsByShopAndUser(product.getShop().getId(), userId)) {
        	throw new ApplicationException("Product not found");
        }

        favoriteProductDao.deleteByProduct(productId);

        cartItemDao.deleteByProduct(productId);
        
        orderItemDao.removeProductRelation(productId);

        var images = product.getImages().stream().map(ProductImage::getName).collect(Collectors.toList());

        productDao.delete(productId);

        var dir = Constants.IMG_PRODUCT_ROOT;

        fileStorageAdapter.delete(dir, images);
    }

}
