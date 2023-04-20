package com.shoppingcenter.data.order;

import java.util.Set;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.product.variant.ProductVariantOptionData;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemEntity extends AuditingEntity {

    @Id
    private long id;

    @Column(columnDefinition = "TEXT")
    private String productName;

    private String productImage;

    private double unitPrice;

    private double discount;

    private int quantity;

    private boolean removed;

    /**
     * JSON string as [{ option: 'option', value: 'value' }]
     */
    // @Column(columnDefinition = "TEXT")
    // private String options;

    @ElementCollection
    @CollectionTable(name = Constants.TABLE_PREFIX + "order_item_option", joinColumns = {
            @JoinColumn(name = "order_item_id")
    })
    private Set<ProductVariantOptionData> options;

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
