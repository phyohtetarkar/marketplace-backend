package com.shoppingcenter.domain.product.usecase;

public interface AddProductToFavoriteUseCase {

    boolean apply(long userId, long productId);

}
