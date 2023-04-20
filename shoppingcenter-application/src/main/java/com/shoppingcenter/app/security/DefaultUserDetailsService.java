package com.shoppingcenter.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.domain.user.User;

public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var entity = repo.findByPhone(username).orElse(null);

        if (entity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (entity.isDisabled()) {
            throw new DisabledException("Account disabled");
        }
        
        var user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setPhone(entity.getPhone());
        user.setEmail(entity.getEmail());
        user.setCreatedAt(entity.getCreatedAt());
        user.setDisabled(entity.isDisabled());
        user.setImage(entity.getImage());
        user.setRole(entity.getRole());
        user.setPassword(entity.getPassword());

        return new UserPrincipal(user);
    }

}
