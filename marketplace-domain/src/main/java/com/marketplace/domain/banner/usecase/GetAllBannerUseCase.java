package com.marketplace.domain.banner.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.banner.Banner;
import com.marketplace.domain.banner.BannerDao;
import com.marketplace.domain.common.SortQuery;

@Component
public class GetAllBannerUseCase {

	@Autowired
    private BannerDao dao;

    public List<Banner> apply() {
        var sort = SortQuery.desc("createdAt");
        return dao.findAll(sort);
    }

}
