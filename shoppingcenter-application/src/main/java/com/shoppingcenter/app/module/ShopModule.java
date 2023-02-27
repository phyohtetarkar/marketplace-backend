package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;
import com.shoppingcenter.domain.shop.dao.ShopReviewDao;
import com.shoppingcenter.domain.shop.usecase.CheckIsShopMemberUseCase;
import com.shoppingcenter.domain.shop.usecase.CheckIsShopMemberUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.CreateShopMemberUseCase;
import com.shoppingcenter.domain.shop.usecase.CreateShopMemberUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.CreateShopUseCase;
import com.shoppingcenter.domain.shop.usecase.CreateShopUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.GetAllShopReviewUseCase;
import com.shoppingcenter.domain.shop.usecase.GetAllShopReviewUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.GetAllShopUseCase;
import com.shoppingcenter.domain.shop.usecase.GetAllShopUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.GetShopBySlugUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopBySlugUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.GetShopByUserUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopByUserUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.GetShopHintsUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopHintsUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.GetShopInsightsUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopInsightsUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.GetShopReviewByUserUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopReviewByUserUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.SaveShopContactUseCase;
import com.shoppingcenter.domain.shop.usecase.SaveShopContactUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.UpdateShopBasicInfoUseCase;
import com.shoppingcenter.domain.shop.usecase.UpdateShopBasicInfoUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.UploadShopCoverUseCase;
import com.shoppingcenter.domain.shop.usecase.UploadShopCoverUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.UploadShopLogoUseCase;
import com.shoppingcenter.domain.shop.usecase.UploadShopLogoUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.ValidateShopActiveUseCase;
import com.shoppingcenter.domain.shop.usecase.ValidateShopActiveUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.ValidateShopMemberUseCase;
import com.shoppingcenter.domain.shop.usecase.ValidateShopMemberUseCaseImpl;
import com.shoppingcenter.domain.shop.usecase.WriteShopReviewUseCase;
import com.shoppingcenter.domain.shop.usecase.WriteShopReviewUseCaseImpl;

@Configuration
public class ShopModule {

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopReviewDao shopReviewDao;

    @Autowired
    private ShopMemberDao shopMemberDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private HTMLStringSanitizer htmlStringSanitizer;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Bean
    CreateShopMemberUseCase createShopMemberUseCase() {
        return new CreateShopMemberUseCaseImpl(shopMemberDao);
    }

    @Bean
    ValidateShopActiveUseCase validateShopActiveUseCase() {
        return new ValidateShopActiveUseCaseImpl(shopDao);
    }

    @Autowired
    ValidateShopMemberUseCase validateShopMemberUseCase() {
        return new ValidateShopMemberUseCaseImpl(shopMemberDao);
    }

    @Bean
    UploadShopLogoUseCase uploadShopLogoUseCase(ValidateShopActiveUseCase validateShopActiveUseCase) {
        var usecase = new UploadShopLogoUseCaseImpl();
        usecase.setDao(shopDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        usecase.setValidateShopActiveUseCase(validateShopActiveUseCase);
        return usecase;
    }

    @Bean
    UploadShopCoverUseCase uploadShopCoverUseCase(ValidateShopActiveUseCase validateShopActiveUseCase) {
        var usecase = new UploadShopCoverUseCaseImpl();
        usecase.setDao(shopDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        usecase.setValidateShopActiveUseCase(validateShopActiveUseCase);
        return usecase;
    }

    @Bean
    SaveShopContactUseCase saveShopContactUseCase(ValidateShopActiveUseCase validateShopActiveUseCase) {
        return new SaveShopContactUseCaseImpl(shopDao, validateShopActiveUseCase);
    }

    @Bean
    CreateShopUseCase createShopUseCase(
            CreateShopMemberUseCase createShopMemberUseCase,
            UploadShopLogoUseCase uploadShopLogoUseCase,
            UploadShopCoverUseCase uploadShopCoverUseCase,
            SaveShopContactUseCase saveShopContactUseCase) {
        var usecase = new CreateShopUseCaseImpl();
        usecase.setShopDao(shopDao);
        usecase.setAuthenticationContext(authenticationContext);
        usecase.setHtmlStringSanitizer(htmlStringSanitizer);
        usecase.setUploadShopLogoUseCase(uploadShopLogoUseCase);
        usecase.setUploadShopCoverUseCase(uploadShopCoverUseCase);
        usecase.setSaveShopContactUseCase(saveShopContactUseCase);
        return usecase;
    }

    @Bean
    UpdateShopBasicInfoUseCase updateShopBasicInfoUseCase(ValidateShopActiveUseCase validateShopActiveUseCase) {
        var usecase = new UpdateShopBasicInfoUseCaseImpl();
        usecase.setDao(shopDao);
        usecase.setHtmlStringSanitizer(htmlStringSanitizer);
        usecase.setValidateShopActiveUseCase(validateShopActiveUseCase);
        return usecase;
    }

    @Bean
    GetShopBySlugUseCase getShopBySlugUseCase() {
        return new GetShopBySlugUseCaseImpl(shopDao);
    }

    @Bean
    GetShopHintsUseCase getShopHintsUseCase() {
        return new GetShopHintsUseCaseImpl(shopDao);
    }

    @Bean
    GetShopByUserUseCase getShopByUserUseCase() {
        return new GetShopByUserUseCaseImpl(shopDao);
    }

    @Bean
    GetAllShopUseCase getAllShopUseCase() {
        return new GetAllShopUseCaseImpl(shopDao);
    }

    @Bean
    GetShopInsightsUseCase getShopInsightsUseCase() {
        var usecase = new GetShopInsightsUseCaseImpl();
        usecase.setShopDao(shopDao);
        usecase.setProductDao(productDao);
        return usecase;
    }

    @Bean
    CheckIsShopMemberUseCase checkIsShopMemberUseCase() {
        return new CheckIsShopMemberUseCaseImpl(shopMemberDao);
    }

    @Bean
    WriteShopReviewUseCase writeShopReviewUseCase() {
        return new WriteShopReviewUseCaseImpl(shopReviewDao, shopDao);
    }

    @Bean
    GetShopReviewByUserUseCase getShopReviewByUserUseCase() {
        return new GetShopReviewByUserUseCaseImpl(shopReviewDao);
    }

    @Bean
    GetAllShopReviewUseCase getAllShopReviewUseCase() {
        return new GetAllShopReviewUseCaseImpl(shopReviewDao);
    }
}
