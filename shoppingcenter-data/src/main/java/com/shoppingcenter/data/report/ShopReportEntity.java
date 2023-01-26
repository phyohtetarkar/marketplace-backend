package com.shoppingcenter.data.report;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.user.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReportEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String message;

    @ManyToOne(optional = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ShopEntity shop;

}
