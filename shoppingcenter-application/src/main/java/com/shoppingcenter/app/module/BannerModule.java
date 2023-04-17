package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.banner.BannerDao;
import com.shoppingcenter.domain.banner.usecase.DeleteBannerUseCase;
import com.shoppingcenter.domain.banner.usecase.GetAllBannerUseCase;
import com.shoppingcenter.domain.banner.usecase.GetBannerByIdUseCase;
import com.shoppingcenter.domain.banner.usecase.SaveBannerUseCase;
import com.shoppingcenter.domain.common.FileStorageAdapter;

@Configuration
public class BannerModule {

    @Autowired
    private BannerDao bannerDao;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Bean
    SaveBannerUseCase saveBannerUseCase() {
        return new SaveBannerUseCase(bannerDao, fileStorageAdapter);
    }

    @Bean
    DeleteBannerUseCase deleteBannerUseCase() {
        return new DeleteBannerUseCase(bannerDao, fileStorageAdapter);
    }

    @Bean
    GetBannerByIdUseCase getBannerByIdUseCase() {
        return new GetBannerByIdUseCase(bannerDao);
    }

    @Bean
    GetAllBannerUseCase getAllBannerUseCase() {
        return new GetAllBannerUseCase(bannerDao);
    }

}
