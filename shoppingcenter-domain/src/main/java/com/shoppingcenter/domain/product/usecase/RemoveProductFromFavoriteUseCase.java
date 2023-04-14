package com.shoppingcenter.domain.product.usecase;

public interface RemoveProductFromFavoriteUseCase {

    void apply(long userId, long productId);

}
