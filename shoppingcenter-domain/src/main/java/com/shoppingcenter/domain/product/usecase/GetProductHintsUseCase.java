package com.shoppingcenter.domain.product.usecase;

import java.util.List;

public interface GetProductHintsUseCase {

    List<String> apply(String q);

}
