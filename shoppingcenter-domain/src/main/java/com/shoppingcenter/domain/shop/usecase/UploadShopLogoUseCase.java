package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.UploadFile;

public interface UploadShopLogoUseCase {

    void apply(long shopId, UploadFile file);

}
