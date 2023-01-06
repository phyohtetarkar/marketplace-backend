package com.shoppingcenter.data.customer;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerEntity {

    private long id;

    @Column(columnDefinition = "TEXT")
    private String name;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(columnDefinition = "TEXT")
    private String userId;
}
