package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.user.UserDao;
import com.shoppingcenter.domain.user.usecase.CreateUserUseCase;
import com.shoppingcenter.domain.user.usecase.CreateUserUseCaseImpl;
import com.shoppingcenter.domain.user.usecase.GetAllUserUseCase;
import com.shoppingcenter.domain.user.usecase.GetAllUserUseCaseImpl;
import com.shoppingcenter.domain.user.usecase.GetUserByIdUseCase;
import com.shoppingcenter.domain.user.usecase.GetUserByIdUseCaseImpl;
import com.shoppingcenter.domain.user.usecase.UpdateUserRoleUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserRoleUseCaseImpl;
import com.shoppingcenter.domain.user.usecase.UpdateUserUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserUseCaseImpl;
import com.shoppingcenter.domain.user.usecase.UploadUserImageUseCase;
import com.shoppingcenter.domain.user.usecase.UploadUserImageUseCaseImpl;

@Configuration
public class UserModule {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Bean
    CreateUserUseCase createUserUseCase() {
        return new CreateUserUseCaseImpl(userDao);
    }

    @Bean
    UpdateUserUseCase updateUserUseCase() {
        return new UpdateUserUseCaseImpl(userDao);
    }

    @Bean
    UpdateUserRoleUseCase updateUserRoleUseCase() {
        return new UpdateUserRoleUseCaseImpl(userDao);
    }

    @Bean
    UploadUserImageUseCase uploadUserImageUseCase() {
        return new UploadUserImageUseCaseImpl(userDao, fileStorageAdapter);
    }

    @Bean
    GetUserByIdUseCase getUserByIdUseCase() {
        return new GetUserByIdUseCaseImpl(userDao);
    }

    @Bean
    GetAllUserUseCase getAllUserUseCase() {
        return new GetAllUserUseCaseImpl(userDao);
    }
}
