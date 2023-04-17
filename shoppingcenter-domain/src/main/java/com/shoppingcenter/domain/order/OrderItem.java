package com.shoppingcenter.domain.order;

import java.util.List;

import com.shoppingcenter.domain.product.ProductVariantOption;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {

    private long id;

    private long productId;

    private long variantId;

    private String productName;

    private String productImage;

    private double unitPrice;

    private double discount;

    private int quantity;

    private boolean removed;

    private List<ProductVariantOption> options;

}
