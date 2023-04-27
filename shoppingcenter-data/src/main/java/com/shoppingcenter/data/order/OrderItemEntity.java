package com.shoppingcenter.data.order;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "OrderItem")
@Table(name = Constants.TABLE_PREFIX + "order_item")
public class OrderItemEntity extends AuditingEntity {

    @Id
    private long id;

    @Column(columnDefinition = "TEXT")
    private String productName;
    
    @Column(columnDefinition = "TEXT")
    private String productSlug;

    private String productImage;

    private double unitPrice;

    private double discount;

    private int quantity;

    private boolean removed;
    
    private String variant;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity order;

    private long productId;

    public OrderItemEntity() {
    }

    public double getSubTotalPrice() {
        return (unitPrice * quantity);
    }

    public double getTotalPrice() {
        return (unitPrice * quantity) - (discount * quantity);
    }

}
