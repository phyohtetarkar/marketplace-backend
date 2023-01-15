package com.shoppingcenter.app.controller.shoppingcart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemEditDTO {

    private long productId;

    private String optionPath;

    private int quantity;

    @Getter
    @Setter
    public static class ID {
        private String userId;

        private long productId;

        private String optionPath;
    }

}
