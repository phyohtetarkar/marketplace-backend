package com.shoppingcenter.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shoppingcenter.data.user.UserMapper;
import com.shoppingcenter.data.user.UserRepo;

public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var entity = repo.findByPhone(username).orElse(null);

        if (entity == null) {
            throw new UsernameNotFoundException("User not found");
        }

//        if (entity.isDisabled()) {
//            throw new DisabledException("Account disabled");
//        }
        
        var user = UserMapper.toDomain(entity);

        return new UserPrincipal(user);
    }

}
