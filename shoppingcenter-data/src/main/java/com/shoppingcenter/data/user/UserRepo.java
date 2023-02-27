package com.shoppingcenter.data.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingcenter.data.user.view.UserRoleView;

public interface UserRepo extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    <T> Optional<T> getUserById(String id, Class<T> type);

    Optional<UserRoleView> getUserByIdAndDisabledFalse(String id);

    boolean existsByIdAndDisabledFalse(String id);

    boolean existsByPhone(String phone);

    @Modifying
    @Query("UPDATE User u SET u.image = :image WHERE u.id = :userId")
    void updateImage(@Param("userId") String userId, @Param("image") String image);

    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :userId")
    void updateRole(@Param("userId") String userId, @Param("role") String role);

    @Modifying
    @Query("UPDATE User u SET u.phone = :phone WHERE u.id = :userId")
    void updatePhoneNumber(@Param("userId") String userId, @Param("phone") String phone);

}
