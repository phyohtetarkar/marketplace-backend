package com.marketplace.domain.banner;

import java.util.List;

import com.marketplace.domain.common.SortQuery;

public interface BannerDao {

    Banner save(BannerInput values);

    void updateImage(int id, String image);

    void delete(int id);

    boolean existsById(int id);

    String getBannerImage(int id);

    Banner findById(int id);

    List<Banner> findAll(SortQuery sort);
}
