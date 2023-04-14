package com.shoppingcenter.domain.product.usecase;

public interface CheckFavoriteProductUseCase {
    boolean apply(long userId, long productId);
}
