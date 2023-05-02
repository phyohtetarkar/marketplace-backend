package com.shoppingcenter.data.product;

import java.util.Set;

import com.shoppingcenter.domain.Constants;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProductAttribute")
@Table(name = Constants.TABLE_PREFIX + "product_attribute")
public class ProductAttributeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private int sort;
	
	@ElementCollection
	@CollectionTable(name = Constants.TABLE_PREFIX + "product_attribute_value", joinColumns = {
			@JoinColumn(name = "attribute_id")
	})
	private Set<ProductAttributeValueEntity> values;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductEntity product;
	
}
