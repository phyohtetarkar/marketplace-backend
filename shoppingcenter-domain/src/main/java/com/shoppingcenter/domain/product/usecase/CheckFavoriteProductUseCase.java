package com.shoppingcenter.domain.product.usecase;

public interface CheckFavoriteProductUseCase {
    boolean apply(String userId, long productId);
}
