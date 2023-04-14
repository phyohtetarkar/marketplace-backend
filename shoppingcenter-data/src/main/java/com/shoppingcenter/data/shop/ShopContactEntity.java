package com.shoppingcenter.data.shop;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopContact")
@Table(name = Constants.TABLE_PREFIX + "shop_contact")
public class ShopContactEntity extends AuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private long id;

    @Column(columnDefinition = "TEXT")
    private String phones; // comma separated

    @Column(columnDefinition = "TEXT")
    private String address;

    private Double latitude;

    private Double longitude;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ShopEntity shop;

    public ShopContactEntity() {
    }
}
