package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shop.ShopMemberInput;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.shop.dao.ShopMemberDao;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class CreateShopMemberUseCase {

	@Autowired
    private ShopMemberDao dao;

	@Autowired
    private ShopDao shopDao;

	@Autowired
    private UserDao userDao;

    public void apply(ShopMemberInput values) {
        if (!shopDao.existsById(values.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        if (!userDao.existsById(values.getUserId())) {
            throw new ApplicationException("User not found");
        }
        dao.save(values);
    }

}
