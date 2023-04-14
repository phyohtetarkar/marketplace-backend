package com.shoppingcenter.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shoppingcenter.domain.user.usecase.GetUserByPhoneUseCase;

public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private GetUserByPhoneUseCase getUserByPhoneUseCase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = getUserByPhoneUseCase.apply(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (user.isDisabled()) {
            throw new DisabledException("Account disabled");
        }

        return new UserPrincipal(user);
    }

}
