package com.shoppingcenter.app.controller.product;

import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.domain.PageData;

public interface FavoriteProductFacade {

    void add(String userId, long productId);

    void remove(long id);

    boolean checkFavorite(String userId, long productId);

    PageData<ProductDTO> findByUser(String userId, Integer page);

}
