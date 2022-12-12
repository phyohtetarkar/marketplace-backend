package com.shoppingcenter.core.user;

import com.shoppingcenter.core.PageResult;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.core.user.model.User;
import com.shoppingcenter.data.user.UserEntity.Role;

public interface UserService {

	void create(User user);

	void update(User user);

	void uploadImage(String userId, UploadFile file);

	void changePhoneNumber(String userId, String phoneNumber);

	void updateRole(String userId, Role role);

	void delete(String id);

	User findById(String id);

	PageResult<User> findAll();

}
