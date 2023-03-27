package com.shoppingcenter.domain.shop.usecase;

import java.util.List;

public interface GetShopHintsUseCase {

    List<String> apply(String q);

}
