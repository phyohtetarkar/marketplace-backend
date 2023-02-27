package com.shoppingcenter.domain.product.usecase;

public interface AddProductToFavoriteUseCase {

    boolean apply(String userId, long productId);

}
