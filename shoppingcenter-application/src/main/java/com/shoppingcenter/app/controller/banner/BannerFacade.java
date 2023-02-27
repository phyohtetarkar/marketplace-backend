package com.shoppingcenter.app.controller.banner;

import java.util.List;

import com.shoppingcenter.app.controller.banner.dto.BannerDTO;
import com.shoppingcenter.app.controller.banner.dto.BannerEditDTO;

public interface BannerFacade {

    void save(BannerEditDTO banner);

    void delete(int id);

    BannerDTO findById(int id);

    List<BannerDTO> findAll();

}
