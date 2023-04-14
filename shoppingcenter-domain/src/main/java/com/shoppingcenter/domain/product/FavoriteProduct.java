package com.shoppingcenter.domain.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteProduct {

    private long productId;

    private long userId;

    private Product product;

    public FavoriteProduct() {

    }

}
