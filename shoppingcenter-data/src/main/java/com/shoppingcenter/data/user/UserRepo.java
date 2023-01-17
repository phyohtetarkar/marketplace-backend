package com.shoppingcenter.data.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, String> {

    Optional<UserRoleView> getUserById(String id);

}
