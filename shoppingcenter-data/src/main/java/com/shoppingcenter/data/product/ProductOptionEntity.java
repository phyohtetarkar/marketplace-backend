package com.shoppingcenter.data.product;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shoppingcenter.data.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProductOption")
@Table(name = Utils.TABLE_PREFIX + "product_option")
public class ProductOptionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private int position;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProductEntity product;

	public ProductOptionEntity() {
	}

}
