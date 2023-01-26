package com.shoppingcenter.data.shop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;
import com.shoppingcenter.data.user.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopMember")
@Table(name = Entities.TABLE_PREFIX + "shop_member")
public class ShopMemberEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(optional = false)
	private ShopEntity shop;

	@ManyToOne(optional = false)
	private UserEntity user;

	private String role;

	public ShopMemberEntity() {
	}

}
