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
import com.shoppingcenter.domain.shop.dao.ShopSearchDao;
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
import com.shoppingcenter.domain.shop.usecase.GetShopByIdUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopByIdUseCaseImpl;
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
import com.shoppingcenter.domain.user.UserDao;

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
    private ShopSearchDao shopSearchDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private HTMLStringSanitizer htmlStringSanitizer;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Bean
    CreateShopMemberUseCase createShopMemberUseCase() {
        var usecase = new CreateShopMemberUseCaseImpl();
        usecase.setDao(shopMemberDao);
        usecase.setShopDao(shopDao);
        usecase.setUserDao(userDao);
        return usecase;
    }

    @Bean
    ValidateShopActiveUseCase validateShopActiveUseCase() {
        return new ValidateShopActiveUseCaseImpl(shopDao);
    }

    @Bean
    ValidateShopMemberUseCase validateShopMemberUseCase() {
        return new ValidateShopMemberUseCaseImpl(shopMemberDao);
    }

    @Bean
    UploadShopLogoUseCase uploadShopLogoUseCase() {
        var usecase = new UploadShopLogoUseCaseImpl();
        usecase.setDao(shopDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        return usecase;
    }

    @Bean
    UploadShopCoverUseCase uploadShopCoverUseCase() {
        var usecase = new UploadShopCoverUseCaseImpl();
        usecase.setDao(shopDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        return usecase;
    }

    @Bean
    SaveShopContactUseCase saveShopContactUseCase() {
        return new SaveShopContactUseCaseImpl(shopDao);
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
        usecase.setCreateShopMemberUseCase(createShopMemberUseCase);
        return usecase;
    }

    @Bean
    UpdateShopBasicInfoUseCase updateShopBasicInfoUseCase() {
        var usecase = new UpdateShopBasicInfoUseCaseImpl();
        usecase.setDao(shopDao);
        usecase.setShopSearchDao(shopSearchDao);
        usecase.setHtmlStringSanitizer(htmlStringSanitizer);
        return usecase;
    }

    @Bean
    GetShopBySlugUseCase getShopBySlugUseCase() {
        return new GetShopBySlugUseCaseImpl(shopDao);
    }

    @Bean
    GetShopByIdUseCase getShopByIdUseCase() {
        return new GetShopByIdUseCaseImpl(shopDao);
    }

    @Bean
    GetShopHintsUseCase getShopHintsUseCase() {
        return new GetShopHintsUseCaseImpl(shopSearchDao);
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
        var usecase = new WriteShopReviewUseCaseImpl();
        usecase.setShopDao(shopDao);
        usecase.setShopReviewDao(shopReviewDao);
        usecase.setUserDao(userDao);
        return usecase;
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
