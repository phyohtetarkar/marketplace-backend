package com.shoppingcenter.domain.shoppingcart.usecase;

import java.util.List;

public interface RemoveProductFromCartUseCase {

    void apply(List<Long> idList);

}
