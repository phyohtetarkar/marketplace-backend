package com.marketplace.domain.user;

import com.marketplace.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserQuery {

    private String name;

    private String phone;
    
    private String email;
    
    private Boolean staffOnly;
    
    private Boolean verified;

    private Integer page;

    public Integer getPage() {
        return Utils.normalizePage(page);
    }

}
