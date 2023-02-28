package com.shoppingcenter.data.user;

import org.springframework.util.StringUtils;

import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.User.Role;

public class UserMapper {

    public static User toDomain(UserEntity entity, String baseUrl) {
        var u = toDomainCompat(entity, baseUrl);
        u.setRole(Role.valueOf(entity.getRole()));
        return u;
    }

    public static User toDomainCompat(UserEntity entity, String baseUrl) {
        var u = new User();
        u.setId(entity.getId());
        u.setName(entity.getName());
        u.setPhone(entity.getPhone());
        u.setEmail(entity.getEmail());
        u.setCreatedAt(entity.getCreatedAt());
        u.setDisabled(entity.isDisabled());
        u.setConfirmed(entity.isConfirmed());

        if (StringUtils.hasText(baseUrl) && StringUtils.hasText(entity.getImage())) {
            u.setImage(baseUrl + "user/" + entity.getImage());
        }
        return u;
    }

}
