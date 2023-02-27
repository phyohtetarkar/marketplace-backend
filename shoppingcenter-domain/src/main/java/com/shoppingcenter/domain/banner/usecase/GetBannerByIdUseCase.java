package com.shoppingcenter.domain.banner.usecase;

import com.shoppingcenter.domain.banner.Banner;

public interface GetBannerByIdUseCase {

    Banner apply(int id);

}
