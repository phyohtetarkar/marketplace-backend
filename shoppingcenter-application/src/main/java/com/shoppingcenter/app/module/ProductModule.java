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
import com.shoppingcenter.domain.product.dao.ProductSearchDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.product.usecase.AddProductToFavoriteUseCase;
import com.shoppingcenter.domain.product.usecase.CheckFavoriteProductUseCase;
import com.shoppingcenter.domain.product.usecase.DeleteProductUseCase;
import com.shoppingcenter.domain.product.usecase.GetAllProductUseCase;
import com.shoppingcenter.domain.product.usecase.GetFavoriteProductByUserUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductBrandsByCategoryUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductByIdUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductBySlugUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductHintsUseCase;
import com.shoppingcenter.domain.product.usecase.GetRelatedProductsUseCase;
import com.shoppingcenter.domain.product.usecase.RemoveProductFromFavoriteUseCase;
import com.shoppingcenter.domain.product.usecase.SaveProductUseCase;
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
    private ProductVariantDao productVariantDao;

    @Autowired
    private ProductSearchDao productSearchDao;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Autowired
    private HTMLStringSanitizer htmlStringSanitizer;

    @Bean
    SaveProductUseCase saveProductUseCase() {
        var usecase = new SaveProductUseCase();
        usecase.setProductDao(productDao);
        usecase.setCategoryDao(categoryDao);
        usecase.setShopDao(shopDao);
        usecase.setImageDao(productImageDao);
        usecase.setVariantDao(productVariantDao);
        usecase.setHtmlStringSanitizer(htmlStringSanitizer);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        return usecase;
    }

    @Bean
    DeleteProductUseCase deleteProductUseCase() {
        var usecase = new DeleteProductUseCase();
        usecase.setProductDao(productDao);
        usecase.setImageDao(productImageDao);
        usecase.setCartItemDao(cartItemDao);
        usecase.setFavoriteProductDao(favoriteProductDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        return usecase;
    }

    @Bean
    GetProductByIdUseCase getProductByIdUseCase() {
        return new GetProductByIdUseCase(productDao);
    }

    @Bean
    GetProductBySlugUseCase getProductBySlugUseCase() {
        return new GetProductBySlugUseCase(productDao);
    }

    @Bean
    GetProductHintsUseCase getProductHintsUseCase() {
        return new GetProductHintsUseCase(productSearchDao);
    }

    @Bean
    GetProductBrandsByCategoryUseCase getProductBrandsByCategoryUseCase() {
        return new GetProductBrandsByCategoryUseCase(productDao);
    }

    @Bean
    GetRelatedProductsUseCase getRelatedProductsUseCase() {
        return new GetRelatedProductsUseCase(productDao);
    }

    @Bean
    GetAllProductUseCase getAllProductUseCase() {
        return new GetAllProductUseCase(productDao);
    }

    @Bean
    AddProductToFavoriteUseCase addProductToFavoriteUseCase() {
        return new AddProductToFavoriteUseCase(favoriteProductDao, productDao);
    }

    @Bean
    RemoveProductFromFavoriteUseCase removeProductFromFavoriteUseCase() {
        return new RemoveProductFromFavoriteUseCase(favoriteProductDao);
    }

    @Bean
    CheckFavoriteProductUseCase checkFavoriteProductUseCase() {
        return new CheckFavoriteProductUseCase(favoriteProductDao);
    }

    @Bean
    GetFavoriteProductByUserUseCase getFavoriteProductByUserUseCase() {
        return new GetFavoriteProductByUserUseCase(favoriteProductDao);
    }

}
