package com.shoppingcenter.domain.banner.usecase;

import java.util.List;

import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.banner.Banner;
import com.shoppingcenter.domain.banner.BannerDao;

public class GetAllBannerUseCaseImpl implements GetAllBannerUseCase {

    private BannerDao dao;

    public GetAllBannerUseCaseImpl(BannerDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Banner> apply() {
        var sort = SortQuery.of("position");
        return dao.findAll(sort);
    }

}
