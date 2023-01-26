package com.shoppingcenter.service.user;

import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.UploadFile;
import com.shoppingcenter.service.user.model.User;

public interface UserService {

	void create(User user);

	void update(User user);

	void uploadImage(String userId, UploadFile file);

	void changePhoneNumber(String userId, String phoneNumber);

	void updateRole(String userId, User.Role role);

	void delete(String id);

	User findById(String id);

	PageData<User> findAll(UserQuery query);

}
