package com.shoppingcenter.domain.banner;

import java.util.List;

import com.shoppingcenter.domain.SortQuery;

public interface BannerDao {

    int save(Banner banner);

    void delete(int id);

    boolean existsById(int id);

    Banner findById(int id);

    List<Banner> findAll(SortQuery sort);
}
