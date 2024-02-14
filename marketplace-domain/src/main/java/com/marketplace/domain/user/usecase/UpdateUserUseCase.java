package com.marketplace.domain.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.user.ProfileUpdateInput;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class UpdateUserUseCase {

	@Autowired
    private UserDao dao;

    public void apply(ProfileUpdateInput values) {
        if (!dao.existsById(values.getUserId())) {
            throw new ApplicationException("User not found");
        }

        if (!Utils.hasText(values.getName())) {
            throw new ApplicationException("Required user name");
        }
        
        if (Utils.hasText(values.getPhone()) && !Utils.isPhoneNumber(values.getPhone())) {
            throw new ApplicationException("Required valid phone number");
        }

        dao.update(values);
    }

}
