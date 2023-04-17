package com.shoppingcenter.domain.banner;

import java.util.List;

import com.shoppingcenter.domain.SortQuery;

public interface BannerDao {

    Banner save(Banner banner);

    void saveAll(List<Banner> list);
    
    void updateImage(int id, String image);

    void delete(int id);

    boolean existsById(int id);

    String getBannerImage(int id);

    Banner findById(int id);

    List<Banner> findAll(SortQuery sort);
}
