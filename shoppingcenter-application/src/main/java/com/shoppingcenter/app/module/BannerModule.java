package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.banner.BannerDao;
import com.shoppingcenter.domain.banner.usecase.DeleteBannerUseCase;
import com.shoppingcenter.domain.banner.usecase.DeleteBannerUseCaseImpl;
import com.shoppingcenter.domain.banner.usecase.GetAllBannerUseCase;
import com.shoppingcenter.domain.banner.usecase.GetAllBannerUseCaseImpl;
import com.shoppingcenter.domain.banner.usecase.GetBannerByIdUseCase;
import com.shoppingcenter.domain.banner.usecase.GetBannerByIdUseCaseImpl;
import com.shoppingcenter.domain.banner.usecase.SaveBannerUseCase;
import com.shoppingcenter.domain.banner.usecase.SaveBannerUseCaseImpl;
import com.shoppingcenter.domain.common.FileStorageAdapter;

@Configuration
public class BannerModule {

    @Autowired
    private BannerDao bannerDao;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Bean
    SaveBannerUseCase saveBannerUseCase() {
        return new SaveBannerUseCaseImpl(bannerDao, fileStorageAdapter);
    }

    @Bean
    DeleteBannerUseCase deleteBannerUseCase() {
        return new DeleteBannerUseCaseImpl(bannerDao, fileStorageAdapter);
    }

    @Bean
    GetBannerByIdUseCase getBannerByIdUseCase() {
        return new GetBannerByIdUseCaseImpl(bannerDao);
    }

    @Bean
    GetAllBannerUseCase getAllBannerUseCase() {
        return new GetAllBannerUseCaseImpl(bannerDao);
    }

}
