package com.shoppingcenter.app.controller.authentication;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.authentication.dto.AuthenticationDTO;
import com.shoppingcenter.app.controller.authentication.dto.LoginDTO;
import com.shoppingcenter.app.controller.authentication.dto.PasswordResetDTO;
import com.shoppingcenter.app.controller.authentication.dto.PhoneNumberVerifyDTO;
import com.shoppingcenter.app.controller.authentication.dto.SignUpDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.security.JwtTokenUtil;
import com.shoppingcenter.app.security.UserPrincipal;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.user.PasswordReset;
import com.shoppingcenter.domain.user.PhoneNumberVerify;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.usecase.CreateUserUseCase;
import com.shoppingcenter.domain.user.usecase.ResetPasswordUseCase;
import com.shoppingcenter.domain.user.usecase.VerifyPhoneNumberUseCase;

@Facade
public class AuthenticationFacade {

    @Autowired
    private CreateUserUseCase createUserUseCase;
    
    @Autowired
    private VerifyPhoneNumberUseCase verifyPhoneNumberUseCase;
    
    @Autowired
    private ResetPasswordUseCase resetPasswordUseCase;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserDetailsService userDetailsService;

    public AuthenticationDTO login(LoginDTO dto) {
        try {
            var authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            var principal = (UserPrincipal) authentication.getPrincipal();
            var accessToken = jwtTokenUtil.generateAccessToken(dto.getUsername());
            var refreshToken = jwtTokenUtil.generateRefreshToken(dto.getUsername());

            var auth = new AuthenticationDTO();
            auth.setAccessToken(accessToken);
            auth.setRefreshToken(refreshToken);
            auth.setUser(modelMapper.map(principal.getUser(), UserDTO.class));
            return auth;
        } catch (BadCredentialsException e) {
            throw new ApplicationException("bad-credentials");
        } catch (DisabledException e) {
            throw new ApplicationException("account-disabled");
        }
    }

    @Transactional
    public AuthenticationDTO signUp(SignUpDTO dto) {
        var user = new User();
        user.setName(dto.getFullName());
        user.setPhone(dto.getUsername());
        user.setPassword(dto.getPassword());

        var result = createUserUseCase.apply(user);
        
        var accessToken = jwtTokenUtil.generateAccessToken(dto.getUsername());
        var refreshToken = jwtTokenUtil.generateRefreshToken(dto.getUsername());

        var auth = new AuthenticationDTO();
        auth.setAccessToken(accessToken);
        auth.setRefreshToken(refreshToken);
        auth.setUser(modelMapper.map(result, UserDTO.class));
        return auth;
    }

    public AuthenticationDTO refresh(String refreshToken) {
        try {
            var claims = jwtTokenUtil.parseToken(refreshToken);
            
            var userDetails = userDetailsService.loadUserByUsername(claims.getSubject());

            var accessToken = jwtTokenUtil.generateAccessToken(userDetails.getUsername());

            var newRefreshToken = jwtTokenUtil.generateRefreshToken(userDetails.getUsername());

            var auth = new AuthenticationDTO();
            auth.setAccessToken(accessToken);
            auth.setRefreshToken(newRefreshToken);
            return auth;
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodes.UNAUTHORIZED, "Unauthorized");
        }
    }
    
    @Transactional
    public void verifyUser(PhoneNumberVerifyDTO dto) {
    	verifyPhoneNumberUseCase.apply(modelMapper.map(dto, PhoneNumberVerify.class));
    }
    
    @Transactional
    public void resetPassword(PasswordResetDTO dto) {
    	resetPasswordUseCase.apply(modelMapper.map(dto, PasswordReset.class));
    }

}
