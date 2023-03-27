package com.shoppingcenter.data.customer;

import com.shoppingcenter.data.AuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String name;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String userId;
}
