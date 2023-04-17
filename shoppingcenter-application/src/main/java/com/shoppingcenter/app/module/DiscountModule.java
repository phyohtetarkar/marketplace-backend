package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.discount.DiscountDao;
import com.shoppingcenter.domain.discount.usecase.ApplyDiscountsUseCase;
import com.shoppingcenter.domain.discount.usecase.DeleteDiscountUseCase;
import com.shoppingcenter.domain.discount.usecase.GetDiscountsByShopUseCase;
import com.shoppingcenter.domain.discount.usecase.RemoveDiscountUseCase;
import com.shoppingcenter.domain.discount.usecase.SaveDiscountUseCase;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;

@Configuration
public class DiscountModule {

    @Autowired
    private DiscountDao discountDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ProductDao productDao;

    @Bean
    SaveDiscountUseCase saveDiscountUseCase() {
        return new SaveDiscountUseCase(discountDao, shopDao);
    }

    @Bean
    DeleteDiscountUseCase deleteDiscountUseCase() {
        var usecase = new DeleteDiscountUseCase();
        usecase.setDiscountDao(discountDao);
        usecase.setProductDao(productDao);
        return usecase;
    }

    @Bean
    ApplyDiscountsUseCase applyDiscountsUseCase() {
        return new ApplyDiscountsUseCase(discountDao);
    }

    @Bean
    RemoveDiscountUseCase removeDiscountUseCase() {
        return new RemoveDiscountUseCase(discountDao);
    }

    @Bean
    GetDiscountsByShopUseCase getDiscountsByShopUseCase() {
        return new GetDiscountsByShopUseCase(discountDao);
    }

}
