package com.shoppingcenter.app.controller.shop;

import java.util.List;

import com.shoppingcenter.app.controller.shop.dto.ShopContactDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopEditDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopGeneralDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopInsightsDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopQuery;

public interface ShopFacade {

    void create(ShopEditDTO shop);

    ShopDTO updateGeneralInfo(ShopGeneralDTO general);

    void updateContact(ShopContactDTO contact);

    void uploadLogo(long shopId, UploadFile file);

    void uploadCover(long shopId, UploadFile file);

    void updateStatus(long shopId, Shop.Status status);

    void delete(long id);

    ShopInsightsDTO getShopInsights(long shopId);

    ShopDTO findById(long id);

    ShopDTO findBySlug(String slug);

    List<String> getHints(String q);

    PageData<ShopDTO> findByUser(String userId, Integer page);

    PageData<ShopDTO> findAll(ShopQuery query);

}
