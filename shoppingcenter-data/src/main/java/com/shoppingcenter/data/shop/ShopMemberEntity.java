package com.shoppingcenter.data.shop;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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

	public enum Role {
		OWNER, ADMIN
	}

	@EmbeddedId
	private ID id;

	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne
	@MapsId("shopId")
	@JoinColumn(name = "shop_id")
	private ShopEntity shop;

	@Enumerated(EnumType.STRING)
	private Role role;

	public ShopMemberEntity() {
		this.id = new ID();
		this.role = Role.ADMIN;
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		private String userId;

		private long shopId;

	}

}
