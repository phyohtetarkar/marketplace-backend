package com.shoppingcenter.service.user;

import com.shoppingcenter.service.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserQuery {

    private String name;

    private String phone;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }
}
