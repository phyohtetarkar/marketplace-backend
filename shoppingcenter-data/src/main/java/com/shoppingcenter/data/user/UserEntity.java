package com.shoppingcenter.data.user;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "User")
@Table(name = Constants.TABLE_PREFIX + "user")
public class UserEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(columnDefinition = "TEXT")
	private String name;

	@Column(unique = true)
	private String phone;

	private String email;

	@Column(columnDefinition = "TEXT")
	private String image;

	private String role;

	private boolean disabled;

	public UserEntity() {
	}

}
