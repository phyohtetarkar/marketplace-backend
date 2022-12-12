package com.shoppingcenter.core.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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

	@JsonProperty(access = Access.READ_ONLY)
	private String image;

	@JsonProperty(access = Access.READ_ONLY)
	private Role role;

	public static User create(UserEntity entity, String baseUrl) {
		User u = new User();
		u.setId(entity.getId());
		u.setName(entity.getName());
		u.setPhone(entity.getPhone());
		u.setEmail(entity.getEmail());
		u.setImage(baseUrl + "users/" + entity.getImage());
		u.setRole(entity.getRole());
		return u;
	}
}
