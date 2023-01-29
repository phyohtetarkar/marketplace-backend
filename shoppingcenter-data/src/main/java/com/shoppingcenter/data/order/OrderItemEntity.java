package com.shoppingcenter.data.order;

import com.shoppingcenter.data.AuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String productName;

    @Column(columnDefinition = "TEXT")
    private String productImage;

    private double unitPrice;

    private double discount;

    private int quantity;

    /**
     * JSON string as [{ option: 'option', value: 'value' }]
     */
    @Column(columnDefinition = "TEXT")
    private String options;

    private long productId;

    private Long variantId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private OrderEntity order;

    public double getTotalPrice() {
        return (unitPrice * quantity) - (discount * quantity);
    }
}
