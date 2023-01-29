package com.shoppingcenter.data.shop;

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
// @Entity(name = "ShopBranch")
// @Table(name = Entities.TABLE_PREFIX + "shop_branch")
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

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ShopEntity shop;

	public ShopBranchEntity() {
	}
}
