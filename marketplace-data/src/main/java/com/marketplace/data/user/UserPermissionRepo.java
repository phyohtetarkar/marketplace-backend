package com.marketplace.data.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionRepo extends JpaRepository<UserPermissionEntity, UserPermissionEntity.ID> {

	void deleteByUserId(long userId);
	
	List<UserPermissionEntity> findById_UserId(long userId);
	
}
