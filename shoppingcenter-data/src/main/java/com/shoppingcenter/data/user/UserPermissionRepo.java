package com.shoppingcenter.data.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionRepo extends JpaRepository<UserPermissionEntity, Long> {

	void deleteByUserId(long userId);
	
}
