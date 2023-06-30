package com.shoppingcenter.data.user;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.user.UserPermission;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "UserPermisson")
@Table(name = Constants.TABLE_PREFIX + "user_permission")
public class UserPermissionEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Enumerated(EnumType.STRING)
	private UserPermission.Permission permission;
	
	@ManyToOne
	private UserEntity user;
}
