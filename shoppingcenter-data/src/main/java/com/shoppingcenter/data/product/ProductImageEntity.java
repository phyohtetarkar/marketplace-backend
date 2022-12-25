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
@Entity
@Table(name = Utils.TABLE_PREFIX + "product_image")
public class ProductImageEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private boolean thumbnail;

	private long size;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProductEntity product;

	public ProductImageEntity() {
	}

}
