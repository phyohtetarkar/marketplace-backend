package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.UploadFile;

public interface UploadShopCoverUseCase {

    void apply(long shopId, UploadFile file);

}
