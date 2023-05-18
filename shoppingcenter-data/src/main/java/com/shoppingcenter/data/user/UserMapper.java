package com.shoppingcenter.data.user;

import com.shoppingcenter.domain.user.User;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        var u = new User();
        u.setId(entity.getId());
        u.setName(entity.getName());
        u.setPhone(entity.getPhone());
        u.setPassword(entity.getPassword());
        u.setEmail(entity.getEmail());
        u.setCreatedAt(entity.getCreatedAt());
        u.setDisabled(entity.isDisabled());
        u.setImage(entity.getImage());
        u.setRole(entity.getRole());
        u.setVerified(entity.isVerified());
//        if (StringUtils.hasText(baseUrl) && StringUtils.hasText(entity.getImage())) {
//            u.setImage(baseUrl + "user/" + entity.getImage());
//        }
        return u;
    }

}
