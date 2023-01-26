package com.shoppingcenter.service;

public class Utils {

    public static int normalizePage(Integer page) {
        return page != null && page >= 0 ? page : 0;
    }

}
