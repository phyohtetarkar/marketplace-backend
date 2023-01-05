package com.shoppingcenter.data.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "User")
@Table(name = Entities.TABLE_PREFIX + "user")
public class UserEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	public enum Gender {
		MALE, FEMALE, NOT_SAY
	}

	public enum Role {
		USER, ADMIN, OWNER
	}

	@Id
	private String id;

	@Column(columnDefinition = "TEXT")
	private String name;

	@Column(unique = true)
	private String phone;

	private String email;

	@Column(columnDefinition = "TEXT")
	private String image;

	@Enumerated(EnumType.STRING)
	private Role role;

	private boolean disabled;

	public UserEntity() {
		this.role = Role.USER;
	}

}
