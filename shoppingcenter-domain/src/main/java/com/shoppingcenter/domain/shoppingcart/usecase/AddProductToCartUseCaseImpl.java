package com.shoppingcenter.domain.shoppingcart.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.product.Product.Status;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.shoppingcart.CartItem;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

import lombok.Setter;

@Setter
public class AddProductToCartUseCaseImpl implements AddProductToCartUseCase {

    private CartItemDao cartItemDao;

    private ProductDao productDao;

    private ProductVariantDao variantDao;

    @Override
    public boolean apply(CartItem item) {
        if (!productDao.existsByIdAndStatus(item.getProductId(), Status.PUBLISHED)) {
            throw new ApplicationException("Product not found");
        }

        if (item.getVariantId() != null && !variantDao.existsById(item.getVariantId())) {
            throw new ApplicationException("Variant not found");
        }

        if (cartItemDao.existsByUserAndProductAndVariant(item.getUserId(), item.getProductId(), item.getVariantId())) {
            return false;
        }

        cartItemDao.save(item);
        return true;
    }

}
