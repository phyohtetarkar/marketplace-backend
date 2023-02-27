package com.shoppingcenter.app.controller.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.controller.user.dto.UserEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.User.Role;
import com.shoppingcenter.domain.user.UserQuery;
import com.shoppingcenter.domain.user.usecase.CreateUserUseCase;
import com.shoppingcenter.domain.user.usecase.GetAllUserUseCase;
import com.shoppingcenter.domain.user.usecase.GetUserByIdUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserRoleUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserUseCase;
import com.shoppingcenter.domain.user.usecase.UploadUserImageUseCase;

@Facade
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @Autowired
    private UpdateUserRoleUseCase updateUserRoleUseCase;

    @Autowired
    private UploadUserImageUseCase uploadUserImageUseCase;

    @Autowired
    private GetUserByIdUseCase getUserByIdUseCase;

    @Autowired
    private GetAllUserUseCase getAllUserUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void create(UserEditDTO user) {
        createUserUseCase.apply(modelMapper.map(user, User.class));
    }

    @Override
    public void update(UserEditDTO user) {
        updateUserUseCase.apply(modelMapper.map(user, User.class));
    }

    @Override
    public void uploadImage(String userId, UploadFile file) {
        uploadUserImageUseCase.apply(userId, file);
    }

    @Override
    public void changePhoneNumber(String userId, String phoneNumber) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateRole(String userId, Role role) {
        updateUserRoleUseCase.apply(userId, role);
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub

    }

    @Override
    public UserDTO findById(String id) {
        return modelMapper.map(getUserByIdUseCase.apply(id), UserDTO.class);
    }

    @Override
    public PageData<UserDTO> findAll(UserQuery query) {
        return modelMapper.map(getAllUserUseCase.apply(query), UserDTO.pageType());
    }

}
