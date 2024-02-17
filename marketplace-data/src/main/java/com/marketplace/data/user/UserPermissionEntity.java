package com.marketplace.data.user;

import java.io.Serializable;
import java.util.Objects;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;
import com.marketplace.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "UserPermisson")
@Table(name = Constants.TABLE_PREFIX + "user_permission")
public class UserPermissionEntity extends AuditingEntity {

	@EmbeddedId
	private UserPermissionEntity.ID id;

	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	public UserPermissionEntity() {
		this.id = new ID();
	}
	
	public User.Permission getPermission() {
		return id.getPermission();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "user_id")
		private long userId;

		@Enumerated(EnumType.STRING)
		private User.Permission permission;

		public ID() {
		}

		public ID(long userId, User.Permission permission) {
			this.userId = userId;
			this.permission = permission;
		}

		@Override
		public int hashCode() {
			return Objects.hash(permission, userId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ID other = (ID) obj;
			return permission == other.permission && userId == other.userId;
		}

	}
}
