package com.shoppingcenter.app.controller.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.controller.user.dto.UserEditDTO;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
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
public class UserFacade {

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

    @Retryable(noRetryFor = { ApplicationException.class })
    @Transactional
    public void create(UserEditDTO user) {
        createUserUseCase.apply(modelMapper.map(user, User.class));
    }

    public void update(UserEditDTO user) {
        updateUserUseCase.apply(modelMapper.map(user, User.class));
    }

    public void uploadImage(long userId, UploadFile file) {
        uploadUserImageUseCase.apply(userId, file);
    }

    public void changePhoneNumber(long userId, String phoneNumber) {
        // TODO Auto-generated method stub

    }

    public void updateRole(long userId, Role role) {
        updateUserRoleUseCase.apply(userId, role);
    }

    public void delete(long id) {
        // TODO Auto-generated method stub

    }

    public UserDTO findById(long id) {
        var user = getUserByIdUseCase.apply(id);
        if (user == null) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "User not found");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    public PageDataDTO<UserDTO> findAll(UserQuery query) {
        return modelMapper.map(getAllUserUseCase.apply(query), UserDTO.pageType());
    }

}
