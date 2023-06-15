package com.shoppingcenter.domain.user;

import com.shoppingcenter.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserQuery {

    private String name;

    private String phone;
    
    private Boolean staffOnly;
    
    private Boolean verified;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }

}
