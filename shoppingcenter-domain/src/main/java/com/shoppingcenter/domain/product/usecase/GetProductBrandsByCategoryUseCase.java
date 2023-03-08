package com.shoppingcenter.domain.product.usecase;

import java.util.List;

public interface GetProductBrandsByCategoryUseCase {

    List<String> apply(String slug);

    List<String> apply(int id);

}
