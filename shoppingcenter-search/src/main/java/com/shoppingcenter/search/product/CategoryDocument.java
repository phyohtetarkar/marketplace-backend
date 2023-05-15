package com.shoppingcenter.search.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDocument {

    private int id;

    private String name;

    private String slug;
    
    private int lft;
    
    private int rgt;
}
