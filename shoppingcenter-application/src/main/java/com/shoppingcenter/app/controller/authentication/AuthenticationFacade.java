package com.shoppingcenter.app.controller.authentication;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.authentication.dto.AuthenticationDTO;
import com.shoppingcenter.app.controller.authentication.dto.LoginDTO;
import com.shoppingcenter.app.controller.authentication.dto.SignUpDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.security.JwtTokenUtil;
import com.shoppingcenter.app.security.UserPrincipal;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.usecase.CreateUserUseCase;

@Facade
public class AuthenticationFacade {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ModelMapper modelMapper;

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

    public AuthenticationDTO signUp(SignUpDTO dto) {
    	// TODO : check otp
    	
        var user = new User();
        user.setName(dto.getFullName());
        user.setPhone(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

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

            var accessToken = jwtTokenUtil.generateAccessToken(claims.getSubject());

            var newRefreshToken = jwtTokenUtil.generateRefreshToken(claims.getSubject());

            var auth = new AuthenticationDTO();
            auth.setAccessToken(accessToken);
            auth.setRefreshToken(newRefreshToken);
            return auth;
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodes.UNAUTHORIZED, "Invalid refresh token");
        }
    }

    public void sendOTP(String phone) {

    }

}
