package com.shoppingcenter.app.controller.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.user.dto.PhoneNumberUpdateDTO;
import com.shoppingcenter.app.controller.user.dto.ProfileStatisticDTO;
import com.shoppingcenter.app.controller.user.dto.UpdateStaffUserDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.controller.user.dto.UserEditDTO;
import com.shoppingcenter.data.user.UserPermissionEntity;
import com.shoppingcenter.data.user.UserPermissionRepo;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.user.PhoneNumberUpdate;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.User.Role;
import com.shoppingcenter.domain.user.UserDao;
import com.shoppingcenter.domain.user.UserQuery;
import com.shoppingcenter.domain.user.usecase.ChangePasswordUseCase;
import com.shoppingcenter.domain.user.usecase.GetAllUserUseCase;
import com.shoppingcenter.domain.user.usecase.GetProfileStatisticUseCase;
import com.shoppingcenter.domain.user.usecase.GetUserByIdUseCase;
import com.shoppingcenter.domain.user.usecase.UpdatePhoneNumberUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserRoleUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserUseCase;
import com.shoppingcenter.domain.user.usecase.UploadUserImageUseCase;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserPermissionRepo userPermissionRepo;
	
	@Autowired
	private UserDao userDao;

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
    private GetProfileStatisticUseCase getProfileStatisticUseCase;

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
    public void verifyUser(long userId) {
    	userDao.updateVerified(userId, true);
    }

    @Transactional
    public void changePassword(long userId, String oldPassword, String newPassword) {
    	changePasswordUseCase.apply(userId, oldPassword, newPassword);
    }
    
    @Transactional
    public void addStaffUser(UpdateStaffUserDTO dto) {
    	var user = userRepo.findByPhone(dto.getPhone()).orElse(null);
    	
    	if (user == null) {
    		throw new ApplicationException("User not found");
    	}
    	
    	if (user.getRole() != User.Role.USER) {
    		throw new ApplicationException("Already staff user");
    	}
    	
    	if (!user.isVerified()) {
    		throw new ApplicationException("User is not verified");
    	}
    	
    	userRepo.updateRole(user.getId(), dto.getRole());
    	
    	if (dto.getPermissions() == null) {
    		return;
    	}
    	
    	var permissions = dto.getPermissions().stream().map(p -> {
    		var up = new UserPermissionEntity();
    		up.setPermission(p);
    		up.setUser(user);    		
    		return up;
    	}).toList();
    	
    	userPermissionRepo.saveAll(permissions);
    }
    
    @Transactional
    public void updateStaffUser(UpdateStaffUserDTO dto) {
    	var user = userRepo.findByPhone(dto.getPhone()).orElse(null);
    	
    	if (user == null) {
    		throw new ApplicationException("User not found");
    	}
    	
    	if (user.getRole() == User.Role.USER) {
    		throw new ApplicationException("Not a staff user");
    	}
    	
    	if (dto.getPermissions() == null) {
    		return;
    	}
    	
    	var permissions = dto.getPermissions().stream().map(p -> {
    		var up = new UserPermissionEntity();
    		up.setPermission(p);
    		up.setUser(user);    		
    		return up;
    	}).toList();
    	
    	userPermissionRepo.deleteByUserId(user.getId());
    	
    	userPermissionRepo.saveAll(permissions);
    }
    
    @Transactional
    public void removeStaffUser(String phone) {
    	var user = userRepo.findByPhone(phone).orElse(null);
    	
    	if (user == null) {
    		throw new ApplicationException("User not found");
    	}
    	
    	if (user.getRole() == User.Role.USER) {
    		throw new ApplicationException("Not a staff user");
    	}
    	
    	userRepo.updateRole(user.getId(), User.Role.USER);
    	
    	userPermissionRepo.deleteByUserId(user.getId());
    }
    
    public ProfileStatisticDTO getProfileStatisitc(long userId) {
    	return modelMapper.map(getProfileStatisticUseCase.apply(userId), ProfileStatisticDTO.class);
    }

    @Transactional(readOnly = true)
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
