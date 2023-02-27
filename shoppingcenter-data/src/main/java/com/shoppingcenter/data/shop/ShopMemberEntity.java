package com.shoppingcenter.data.shop;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopMember")
@Table(name = Constants.TABLE_PREFIX + "shop_member")
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
