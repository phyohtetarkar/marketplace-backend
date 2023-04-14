package com.shoppingcenter.app.controller.product;

import com.shoppingcenter.app.controller.product.dto.FavoriteProductDTO;
import com.shoppingcenter.domain.PageData;

public interface FavoriteProductFacade {

    void add(long userId, long productId);

    void remove(long userId, long productId);

    boolean checkFavorite(long userId, long productId);

    PageData<FavoriteProductDTO> findByUser(long userId, Integer page);

}
