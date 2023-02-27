package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;
import com.shoppingcenter.domain.shoppingcart.usecase.AddProductToCartUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.AddProductToCartUseCaseImpl;
import com.shoppingcenter.domain.shoppingcart.usecase.CountCartItemByUserUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.CountCartItemByUserUseCaseImpl;
import com.shoppingcenter.domain.shoppingcart.usecase.GetCartItemsByUserUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.GetCartItemsByUserUseCaseImpl;
import com.shoppingcenter.domain.shoppingcart.usecase.RemoveProductFromCartUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.RemoveProductFromCartUseCaseImpl;
import com.shoppingcenter.domain.shoppingcart.usecase.UpdateCartItemQuantityUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.UpdateCartItemQuantityUseCaseImpl;

@Configuration
public class ShoppingCartModule {

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductVariantDao productVariantDao;

    @Bean
    AddProductToCartUseCase addProductToCartUseCase() {
        var usecase = new AddProductToCartUseCaseImpl();
        usecase.setCartItemDao(cartItemDao);
        usecase.setProductDao(productDao);
        usecase.setVariantDao(productVariantDao);
        return usecase;
    }

    @Bean
    UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase() {
        return new UpdateCartItemQuantityUseCaseImpl(cartItemDao);
    }

    @Bean
    RemoveProductFromCartUseCase removeProductFromCartUseCase() {
        return new RemoveProductFromCartUseCaseImpl(cartItemDao);
    }

    @Bean
    CountCartItemByUserUseCase countCartItemByUserUseCase() {
        return new CountCartItemByUserUseCaseImpl(cartItemDao);
    }

    @Bean
    GetCartItemsByUserUseCase getCartItemsByUserUseCase() {
        return new GetCartItemsByUserUseCaseImpl(cartItemDao);
    }
}
