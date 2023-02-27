package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.category.CategoryDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductImageDao;
import com.shoppingcenter.domain.product.dao.ProductOptionDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.product.usecase.AddProductToFavoriteUseCase;
import com.shoppingcenter.domain.product.usecase.AddProductToFavoriteUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.CheckFavoriteProductUseCase;
import com.shoppingcenter.domain.product.usecase.CheckFavoriteProductUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.DeleteProductUseCase;
import com.shoppingcenter.domain.product.usecase.DeleteProductUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.GetAllProductUseCase;
import com.shoppingcenter.domain.product.usecase.GetAllProductUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.GetFavoriteProductByUserUseCase;
import com.shoppingcenter.domain.product.usecase.GetFavoriteProductByUserUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.GetProductBrandsByCategoryUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductBrandsByCategoryUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.GetProductByIdUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductByIdUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.GetProductBySlugUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductBySlugUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.GetProductHintsUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductHintsUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.GetRelatedProductsUseCase;
import com.shoppingcenter.domain.product.usecase.GetRelatedProductsUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.RemoveProductFromFavoriteUseCase;
import com.shoppingcenter.domain.product.usecase.RemoveProductFromFavoriteUseCaseImpl;
import com.shoppingcenter.domain.product.usecase.SaveProductUseCase;
import com.shoppingcenter.domain.product.usecase.SaveProductUseCaseImpl;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

@Configuration
public class ProductModule {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private FavoriteProductDao favoriteProductDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductImageDao productImageDao;

    @Autowired
    private ProductOptionDao productOptionDao;

    @Autowired
    private ProductVariantDao productVariantDao;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Autowired
    private HTMLStringSanitizer htmlStringSanitizer;

    @Bean
    SaveProductUseCase saveProductUseCase() {
        var usecase = new SaveProductUseCaseImpl();
        usecase.setProductDao(productDao);
        usecase.setCategoryDao(categoryDao);
        usecase.setShopDao(shopDao);
        usecase.setImageDao(productImageDao);
        usecase.setOptionDao(productOptionDao);
        usecase.setVariantDao(productVariantDao);
        usecase.setHtmlStringSanitizer(htmlStringSanitizer);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        return usecase;
    }

    @Bean
    DeleteProductUseCase deleteProductUseCase() {
        var usecase = new DeleteProductUseCaseImpl();
        usecase.setProductDao(productDao);
        usecase.setImageDao(productImageDao);
        usecase.setCartItemDao(cartItemDao);
        usecase.setFavoriteProductDao(favoriteProductDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        return usecase;
    }

    @Bean
    GetProductByIdUseCase getProductByIdUseCase() {
        return new GetProductByIdUseCaseImpl(productDao);
    }

    @Bean
    GetProductBySlugUseCase getProductBySlugUseCase() {
        return new GetProductBySlugUseCaseImpl(productDao);
    }

    @Bean
    GetProductHintsUseCase getProductHintsUseCase() {
        return new GetProductHintsUseCaseImpl(productDao);
    }

    @Bean
    GetProductBrandsByCategoryUseCase getProductBrandsByCategoryUseCase() {
        return new GetProductBrandsByCategoryUseCaseImpl(productDao);
    }

    @Bean
    GetRelatedProductsUseCase getRelatedProductsUseCase() {
        return new GetRelatedProductsUseCaseImpl(productDao);
    }

    @Bean
    GetAllProductUseCase getAllProductUseCase() {
        return new GetAllProductUseCaseImpl(productDao);
    }

    @Bean
    AddProductToFavoriteUseCase addProductToFavoriteUseCase() {
        return new AddProductToFavoriteUseCaseImpl(favoriteProductDao, productDao);
    }

    @Bean
    RemoveProductFromFavoriteUseCase removeProductFromFavoriteUseCase() {
        return new RemoveProductFromFavoriteUseCaseImpl(favoriteProductDao);
    }

    @Bean
    CheckFavoriteProductUseCase checkFavoriteProductUseCase() {
        return new CheckFavoriteProductUseCaseImpl(favoriteProductDao);
    }

    @Bean
    GetFavoriteProductByUserUseCase getFavoriteProductByUserUseCase() {
        return new GetFavoriteProductByUserUseCaseImpl(favoriteProductDao);
    }

}
