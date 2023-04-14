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

    void uploadImage(long userId, UploadFile file);

    void changePhoneNumber(long userId, String phoneNumber);

    void updateRole(long userId, User.Role role);

    void delete(long id);

    UserDTO findById(long id);

    PageData<UserDTO> findAll(UserQuery query);

}
