package com.marketplace.data.order;

import java.math.BigDecimal;

import com.marketplace.data.AuditingEntity;
import com.marketplace.data.product.ProductEntity;
import com.marketplace.data.product.ProductVariantEntity;
import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal discountPrice;

    private int quantity;
    
    private boolean cancelled;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity order;
    
    @ManyToOne
    private ProductEntity product;
    
    @ManyToOne
    private ProductVariantEntity productVariant;

    public OrderItemEntity() {
    }

    public BigDecimal getSubTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getTotalPrice() {
        return getSubTotalPrice().subtract(discountPrice.multiply(BigDecimal.valueOf(quantity)));
    }

}
