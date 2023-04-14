package com.shoppingcenter.data.order;

import java.io.Serializable;
import java.util.Set;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.product.variant.ProductVariantOptionData;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemEntity extends AuditingEntity {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OrderItemEntity.ID id;

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

    @MapsId("order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public OrderItemEntity() {
        this.id = new ID();
    }

    public double getSubTotalPrice() {
        return (unitPrice * quantity);
    }

    public double getTotalPrice() {
        return (unitPrice * quantity) - (discount * quantity);
    }

    @Getter
    @Setter
    @Embeddable
    public static class ID implements Serializable {

        @Column(name = "order_id")
        private long orderId;

        @Column(name = "product_id")
        private long productId;

        public ID() {
        }

        public ID(long orderId, long productId) {
            this.orderId = orderId;
            this.productId = productId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (orderId ^ (orderId >>> 32));
            result = prime * result + (int) (productId ^ (productId >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ID other = (ID) obj;
            if (orderId != other.orderId)
                return false;
            if (productId != other.productId)
                return false;
            return true;
        }

    }
}
