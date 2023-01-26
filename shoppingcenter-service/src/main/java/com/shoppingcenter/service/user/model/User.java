package com.shoppingcenter.service.user.model;

import org.springframework.util.StringUtils;

import com.shoppingcenter.data.user.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	public enum Role {
		USER, ADMIN, OWNER
	}

	private String id;

	private String name;

	private String phone;

	private String email;

	private String image;

	private Role role;

	private boolean disabled;

	private long createdAt;

	public static User create(UserEntity entity, String baseUrl) {
		User u = createCompat(entity, baseUrl);
		u.setRole(Role.valueOf(entity.getRole()));
		return u;
	}

	public static User createCompat(UserEntity entity, String baseUrl) {
		User u = new User();
		u.setId(entity.getId());
		u.setName(entity.getName());
		u.setPhone(entity.getPhone());
		u.setEmail(entity.getEmail());
		u.setCreatedAt(entity.getCreatedAt());
		u.setDisabled(entity.isDisabled());

		if (StringUtils.hasText(entity.getImage())) {
			u.setImage(baseUrl + "user/" + entity.getImage());
		}
		return u;
	}
}
