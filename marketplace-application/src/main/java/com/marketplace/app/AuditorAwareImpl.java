package com.marketplace.app;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.domain.user.User;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(AuthenticationUtil.getAuthenticatedUser())
                .map(User::getUid);
    }

}
