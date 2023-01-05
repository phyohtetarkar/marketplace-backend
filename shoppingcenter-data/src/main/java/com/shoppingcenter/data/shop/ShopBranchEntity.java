package com.shoppingcenter.data.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopBranch")
@Table(name = Entities.TABLE_PREFIX + "shop_branch")
public class ShopBranchEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String name;

	@Column(columnDefinition = "TEXT")
	private String phones; // comma separated

	@Column(columnDefinition = "TEXT")
	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
	private ShopEntity shop;

	public ShopBranchEntity() {
	}
}
