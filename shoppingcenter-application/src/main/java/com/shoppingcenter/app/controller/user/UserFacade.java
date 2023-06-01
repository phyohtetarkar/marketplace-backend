package com.shoppingcenter.app.controller.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.user.dto.PhoneNumberUpdateDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.controller.user.dto.UserEditDTO;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.user.PhoneNumberUpdate;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.User.Role;
import com.shoppingcenter.domain.user.UserQuery;
import com.shoppingcenter.domain.user.usecase.ChangePasswordUseCase;
import com.shoppingcenter.domain.user.usecase.GetAllUserUseCase;
import com.shoppingcenter.domain.user.usecase.GetUserByIdUseCase;
import com.shoppingcenter.domain.user.usecase.UpdatePhoneNumberUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserRoleUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserUseCase;
import com.shoppingcenter.domain.user.usecase.UploadUserImageUseCase;

@Facade
public class UserFacade {

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
    private UpdatePhoneNumberUseCase updatePhoneNumberUseCase;
    
    @Autowired
    private ChangePasswordUseCase changePasswordUseCase;

    @Autowired
    private ModelMapper modelMapper;
    
    @Transactional
    public void update(UserEditDTO user) {
        updateUserUseCase.apply(modelMapper.map(user, User.class));
    }

    @Transactional
    public void uploadImage(long userId, UploadFile file) {
        uploadUserImageUseCase.apply(userId, file);
    }

    @Transactional
    public void changePhoneNumber(PhoneNumberUpdateDTO dto) {
    	updatePhoneNumberUseCase.apply(modelMapper.map(dto, PhoneNumberUpdate.class));
    }

    @Transactional
    public void updateRole(long userId, Role role) {
        updateUserRoleUseCase.apply(userId, role);
    }
    
    @Transactional
    public void updateRole(String phone, Role role) {
        updateUserRoleUseCase.apply(phone, role);
    }

    @Transactional
    public void changePassword(long userId, String oldPassword, String newPassword) {
    	changePasswordUseCase.apply(userId, oldPassword, newPassword);
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
