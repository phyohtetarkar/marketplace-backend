package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.common.PasswordEncoderAdapter;
import com.shoppingcenter.domain.misc.OTPAttemptDao;
import com.shoppingcenter.domain.misc.usecase.VerifyOTPUseCase;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;
import com.shoppingcenter.domain.user.UserDao;
import com.shoppingcenter.domain.user.usecase.ChangePasswordUseCase;
import com.shoppingcenter.domain.user.usecase.CheckUserExistsByPhoneUseCase;
import com.shoppingcenter.domain.user.usecase.CreateUserUseCase;
import com.shoppingcenter.domain.user.usecase.GetAllUserUseCase;
import com.shoppingcenter.domain.user.usecase.GetProfileStatisticUseCase;
import com.shoppingcenter.domain.user.usecase.GetUserByIdUseCase;
import com.shoppingcenter.domain.user.usecase.GetUserByPhoneUseCase;
import com.shoppingcenter.domain.user.usecase.ResetPasswordUseCase;
import com.shoppingcenter.domain.user.usecase.UpdatePhoneNumberUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserRoleUseCase;
import com.shoppingcenter.domain.user.usecase.UpdateUserUseCase;
import com.shoppingcenter.domain.user.usecase.UploadUserImageUseCase;
import com.shoppingcenter.domain.user.usecase.VerifyPhoneNumberUseCase;

@Configuration
public class UserModule {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private OTPAttemptDao otpAttemptDao;
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private FavoriteProductDao favoriteProductDao;
    
    @Autowired
    private ShopMemberDao shopMemberDao;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;
    
    @Autowired
    private PasswordEncoderAdapter passwordEncoderAdapter;

    @Bean
    CreateUserUseCase createUserUseCase() {
        var usecase = new CreateUserUseCase();
        usecase.setDao(userDao);
        usecase.setPasswordEncoderAdapter(passwordEncoderAdapter);
        return usecase;
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
    
    @Bean
    UpdatePhoneNumberUseCase updatePhoneNumberUseCase(VerifyOTPUseCase verifyOTPUseCase) {
    	var usecase = new UpdatePhoneNumberUseCase();
    	usecase.setDao(userDao);
    	usecase.setOtpAttemptDao(otpAttemptDao);
    	usecase.setVerifyOTPUseCase(verifyOTPUseCase);
    	return usecase;
    }
    
    @Bean
    ResetPasswordUseCase resetPasswordUseCase(VerifyOTPUseCase verifyOTPUseCase) {
    	var usecase = new ResetPasswordUseCase();
    	usecase.setDao(userDao);
    	usecase.setOtpAttemptDao(otpAttemptDao);
    	usecase.setVerifyOTPUseCase(verifyOTPUseCase);
    	usecase.setPasswordEncoderAdapter(passwordEncoderAdapter);
    	return usecase;
    }
    
    @Bean
    VerifyPhoneNumberUseCase verifyPhoneNumberUseCase(VerifyOTPUseCase verifyOTPUseCase) {
    	var usecase = new VerifyPhoneNumberUseCase();
    	usecase.setUserDao(userDao);
    	usecase.setOtpAttemptDao(otpAttemptDao);
    	usecase.setVerifyOTPUseCase(verifyOTPUseCase);
    	return usecase;
    }
    
    @Bean
    ChangePasswordUseCase changePasswordUseCase() {
    	var usecase = new ChangePasswordUseCase();
    	usecase.setUserDao(userDao);
    	usecase.setPasswordEncoderAdapter(passwordEncoderAdapter);
    	return usecase;
    }
    
    @Bean
    GetProfileStatisticUseCase getProfileStatisticUseCase() {
    	var usecase = new GetProfileStatisticUseCase();
    	usecase.setFavoriteProductDao(favoriteProductDao);
    	usecase.setOrderDao(orderDao);
    	usecase.setShopMemberDao(shopMemberDao);
    	return usecase;
    }
}
