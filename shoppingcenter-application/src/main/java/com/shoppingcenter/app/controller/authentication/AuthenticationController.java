package com.shoppingcenter.app.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.authentication.dto.AuthenticationDTO;
import com.shoppingcenter.app.controller.authentication.dto.LoginDTO;
import com.shoppingcenter.app.controller.authentication.dto.SignUpDTO;
import com.shoppingcenter.app.security.JwtTokenFilter;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.common.AppProperties;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private AppProperties properties;

    @PostMapping("sign-in")
    public ResponseEntity<AuthenticationDTO> login(@RequestBody LoginDTO dto) {
        var data = authenticationFacade.login(dto);

        var headers = buildRefreshTokenHeader(data.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(data);
    }

    @PostMapping("sign-up")
    public ResponseEntity<AuthenticationDTO> signUp(@RequestBody SignUpDTO dto) {
        var data = authenticationFacade.signUp(dto);
        var headers = buildRefreshTokenHeader(data.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(data);
    }

    @PostMapping("refresh")
    public ResponseEntity<AuthenticationDTO> refresh(
            @RequestParam(name = "refresh-token", required = false) String token, HttpServletRequest req) {
    	var cookies = req.getCookies();
    	String refreshToken = null;
    	
    	if (cookies != null) {
    		for (var cookie : cookies) {
    			if (JwtTokenFilter.REFRESH_TOKEN_KEY.equals(cookie.getName())) {
    				refreshToken = cookie.getValue();
    				break;
    			}
    		}
    	}
    	
        var rt = StringUtils.hasText(token) ? token : refreshToken;
        
        if (rt == null) {
        	throw new ApplicationException(ErrorCodes.UNAUTHORIZED, "Invalid refresh token");
        }

        var data = authenticationFacade.refresh(rt);

        var headers = buildRefreshTokenHeader(data.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(data);
    }

//    @GetMapping("csrf")
//    public CsrfToken getCsrfToken(HttpServletRequest request) {
//        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//    }
    
    @PostMapping("sign-out")
    public ResponseEntity<?> signOut() {
    	var headers = new HttpHeaders();
        headers.add("Set-Cookie", String.format("%s=%s; Max-Age=%d; Path=/; Domain=%s; HttpOnly",
                JwtTokenFilter.REFRESH_TOKEN_KEY, "", 0, properties.getCookieDomain()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("success");
    }

    private HttpHeaders buildRefreshTokenHeader(String token) {
        long maxAge = 30 * 24 * 60 * 60;

        var domain = properties.getCookieDomain();

        var secured = !"localhost".equals(domain) ? "Secure" : "";

        var headers = new HttpHeaders();
        headers.add("Set-Cookie",
                String.format("%s=%s; Max-Age=%d; Priority=High; Path=/; Domain=%s; HttpOnly; %s",
                        JwtTokenFilter.REFRESH_TOKEN_KEY, token, maxAge, domain, secured));

        return headers;
    }
}
