package com.shoppingcenter.service.product;

import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.product.model.Product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductQuery {

    private String q;

    private Boolean discount;

    private String brand;

    private Product.Status status;

    private Double maxPrice;

    private Long shopId;

    private Integer categoryId;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }

}
