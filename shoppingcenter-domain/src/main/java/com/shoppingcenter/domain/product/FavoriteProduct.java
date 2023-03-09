package com.shoppingcenter.domain.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteProduct {

    private long id;

    private Product product;

    public FavoriteProduct() {

    }

    public FavoriteProduct(long id, Product product) {
        this.id = id;
        this.product = product;
    }

}
