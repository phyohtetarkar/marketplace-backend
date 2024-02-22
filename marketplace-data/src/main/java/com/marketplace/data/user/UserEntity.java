package com.marketplace.data.user;

import java.util.List;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;
import com.marketplace.domain.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "User")
@Table(name = Constants.TABLE_PREFIX + "user")
public class UserEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true, nullable = false)
	private String uid;

	@Column(columnDefinition = "TEXT")
	private String name;
	
	private String phone;

	private String email;

	@Column(columnDefinition = "TEXT")
	private String image;

	@Enumerated(EnumType.STRING)
	private User.Role role;

	private boolean disabled;
	
	private boolean deleted;
	
	@OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<UserPermissionEntity> permissions;

	public UserEntity() {
	}

}
