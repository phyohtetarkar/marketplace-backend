package com.shoppingcenter.core.user.model;

import org.springframework.util.StringUtils;

import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.data.user.UserEntity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private String id;

	private String name;

	private String phone;

	private String email;

	private String image;

	private Role role;

	private long createdAt;

	public static User create(UserEntity entity, String baseUrl) {
		User u = createCompat(entity, baseUrl);
		u.setRole(entity.getRole());
		return u;
	}

	public static User createCompat(UserEntity entity, String baseUrl) {
		User u = new User();
		u.setId(entity.getId());
		u.setName(entity.getName());
		u.setPhone(entity.getPhone());
		u.setEmail(entity.getEmail());
		u.setCreatedAt(entity.getCreatedAt());

		if (StringUtils.hasText(entity.getImage())) {
			u.setImage(baseUrl + "users/" + entity.getImage());
		}
		return u;
	}
}
