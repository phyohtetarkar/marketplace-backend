package com.shoppingcenter.app.controller.user;

import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.controller.user.dto.UserEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserQuery;

public interface UserFacade {

    void create(UserEditDTO user);

    void update(UserEditDTO user);

    void uploadImage(String userId, UploadFile file);

    void changePhoneNumber(String userId, String phoneNumber);

    void updateRole(String userId, User.Role role);

    void delete(String id);

    UserDTO findById(String id);

    PageData<UserDTO> findAll(UserQuery query);

}
