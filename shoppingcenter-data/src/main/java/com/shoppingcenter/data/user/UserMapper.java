package com.shoppingcenter.data.user;

import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserPermission;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        var u = toDomainCompat(entity);
        if (entity.getPermissions() != null) {
        	var permissions = entity.getPermissions().stream().map(up -> {
        		var p = new UserPermission();
        		p.setId(up.getId());
        		p.setPermission(up.getPermission());
        		return p;
        	}).toList();
        	u.setPermissions(permissions);
        }
        return u;
    }
    
    public static User toDomainCompat(UserEntity entity) {
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
        return u;
    }

}
