package com.shoppingcenter.domain.user;

import com.shoppingcenter.domain.PageData;

public interface UserDao {

    void create(User user);

    void update(String userId, String name, String email);

    void updateImage(String userId, String fileName);

    void updatePhoneNumber(String userId, String phoneNumber);

    void updateRole(String userId, User.Role role);

    void delete(String id);

    boolean existsById(String id);

    boolean existsByPhone(String phoneNumber);

    String getImage(String id);

    User findById(String id);

    PageData<User> findAll(UserQuery query);
}
