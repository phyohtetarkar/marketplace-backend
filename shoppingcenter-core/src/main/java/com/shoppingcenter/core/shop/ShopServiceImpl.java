package com.shoppingcenter.core.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcenter.data.shop.ShopRepo;

@Service
public class ShopServiceImpl {

    @Autowired
    private ShopRepo shopRepo;

}
