package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.user.UserDao;
import com.shoppingcenter.domain.user.usecase.CheckUserExistsByPhoneUseCase;
import com.shoppingcenter.domain.user.usecase.CreateUserUseCase;
import com.shoppingcenter.domain.user.usecase.GetAllUserUseCase;
import com.shoppingcenter.domain.user.usecase.GetUserByIdUseCase;
import com.shoppingcenter.domain.user.usecase.GetUserByPhoneUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserRoleUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserUseCase;
import com.shoppingcenter.domain.user.usecase.UploadUserImageUseCase;

@Configuration
public class UserModule {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Bean
    CreateUserUseCase createUserUseCase() {
        return new CreateUserUseCase(userDao);
    }

    @Bean
    UpdateUserUseCase updateUserUseCase() {
        return new UpdateUserUseCase(userDao);
    }

    @Bean
    UpdateUserRoleUseCase updateUserRoleUseCase() {
        return new UpdateUserRoleUseCase(userDao);
    }

    @Bean
    UploadUserImageUseCase uploadUserImageUseCase() {
        return new UploadUserImageUseCase(userDao, fileStorageAdapter);
    }

    @Bean
    GetUserByIdUseCase getUserByIdUseCase() {
        return new GetUserByIdUseCase(userDao);
    }

    @Bean
    GetUserByPhoneUseCase getUserByPhoneUseCase() {
        return new GetUserByPhoneUseCase(userDao);
    }

    @Bean
    GetAllUserUseCase getAllUserUseCase() {
        return new GetAllUserUseCase(userDao);
    }
    
    @Bean
    CheckUserExistsByPhoneUseCase checkUserExistsByPhoneUseCase() {
    	return new CheckUserExistsByPhoneUseCase(userDao);
    }
}
