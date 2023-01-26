package com.shoppingcenter.data.user;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressEntity {

    private long id;

    @Column(columnDefinition = "TEXT")
    private String name;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    private UserEntity user;
}
