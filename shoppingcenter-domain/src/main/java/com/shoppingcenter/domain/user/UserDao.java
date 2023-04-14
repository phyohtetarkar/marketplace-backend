package com.shoppingcenter.domain.user;

import com.shoppingcenter.domain.PageData;

public interface UserDao {

    User create(User user);

    void update(long userId, String name, String email);

    void updateImage(long userId, String fileName);

    void updatePhoneNumber(long userId, String phoneNumber);

    void updateRole(long userId, User.Role role);

    void delete(long id);

    boolean existsById(long id);

    boolean existsByPhone(String phoneNumber);

    String getImage(long id);

    User findById(long id);

    User findByPhone(String phone);

    PageData<User> findAll(UserQuery query);
}
