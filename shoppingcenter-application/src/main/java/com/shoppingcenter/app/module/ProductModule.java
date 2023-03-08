package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.category.CategoryDao;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductImageDao;
import com.shoppingcenter.domain.product.dao.ProductSearchDao;
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
import com.shoppingcenter.domain.shop.usecase.ValidateShopActiveUseCase;
import com.shoppingcenter.domain.shop.usecase.ValidateShopMemberUseCase;
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

    @Autowired
    private AuthenticationContext authenticationContext;

    @Bean
    SaveProductUseCase saveProductUseCase(
            ValidateShopMemberUseCase validateShopMemberUseCase,
            ValidateShopActiveUseCase validateShopActiveUseCase) {
        var usecase = new SaveProductUseCaseImpl();
        usecase.setProductDao(productDao);
        usecase.setCategoryDao(categoryDao);
        usecase.setShopDao(shopDao);
        usecase.setImageDao(productImageDao);
        usecase.setVariantDao(productVariantDao);
        usecase.setHtmlStringSanitizer(htmlStringSanitizer);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        usecase.setValidateShopMemberUseCase(validateShopMemberUseCase);
        usecase.setValidateShopActiveUseCase(validateShopActiveUseCase);
        usecase.setAuthenticationContext(authenticationContext);
        return usecase;
    }

    @Bean
    DeleteProductUseCase deleteProductUseCase(
            ValidateShopMemberUseCase validateShopMemberUseCase,
            ValidateShopActiveUseCase validateShopActiveUseCase) {
        var usecase = new DeleteProductUseCaseImpl();
        usecase.setProductDao(productDao);
        usecase.setImageDao(productImageDao);
        usecase.setCartItemDao(cartItemDao);
        usecase.setFavoriteProductDao(favoriteProductDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        usecase.setValidateShopMemberUseCase(validateShopMemberUseCase);
        usecase.setValidateShopActiveUseCase(validateShopActiveUseCase);
        usecase.setAuthenticationContext(authenticationContext);
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
        return new GetProductHintsUseCaseImpl(productSearchDao);
    }

    @Bean
    GetProductBrandsByCategoryUseCase getProductBrandsByCategoryUseCase() {
        return new GetProductBrandsByCategoryUseCaseImpl(productDao);
    }

    @Bean
    GetRelatedProductsUseCase getRelatedProductsUseCase() {
        return new GetRelatedProductsUseCaseImpl(productSearchDao);
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
