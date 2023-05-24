package com.shoppingcenter.app.controller.authentication;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.common.AppProperties;
import com.shoppingcenter.app.controller.authentication.dto.AuthenticationDTO;
import com.shoppingcenter.app.controller.authentication.dto.LoginDTO;
import com.shoppingcenter.app.controller.authentication.dto.PasswordResetDTO;
import com.shoppingcenter.app.controller.authentication.dto.PhoneNumberVerifyDTO;
import com.shoppingcenter.app.controller.authentication.dto.SignUpDTO;
import com.shoppingcenter.app.security.JwtTokenFilter;
import com.shoppingcenter.domain.common.AuthenticationContext;

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
    
    @Autowired
    private AuthenticationContext authentication;

    @PostMapping("sign-in")
    public ResponseEntity<AuthenticationDTO> login(@RequestBody LoginDTO dto) {
        var data = authenticationFacade.login(dto);

        var headers = buildTokenHeader(data.getAccessToken(), data.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(data);
    }

    @PostMapping("sign-up")
    public ResponseEntity<AuthenticationDTO> signUp(@RequestBody SignUpDTO dto) {
        var data = authenticationFacade.signUp(dto);
        var headers = buildTokenHeader(data.getAccessToken(), data.getRefreshToken());
        
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(data);
    }
    
    @PostMapping("verify")
    public void verifyPhoneNumber(@RequestBody PhoneNumberVerifyDTO dto) {
    	dto.setUserId(authentication.getUserId());
    	authenticationFacade.verifyUser(dto);
    }
    
    @PostMapping("reset-password")
    public void resetPassword(@RequestBody PasswordResetDTO dto) {
    	authenticationFacade.resetPassword(dto);
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refresh(
            @RequestParam(name = "refresh-token", required = false) String token, HttpServletRequest req) {
    	try {
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
            	throw new RuntimeException("Unauthorized");
            }

            var data = authenticationFacade.refresh(rt);

            var headers = buildTokenHeader(data.getAccessToken(), data.getRefreshToken());

            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(data);
		} catch (Exception e) {
			var headers = clearCookies();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body(e.getMessage());
		}
    	
    }

//    @GetMapping("csrf")
//    public CsrfToken getCsrfToken(HttpServletRequest request) {
//        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//    }
    
    @PostMapping("sign-out")
    public ResponseEntity<?> signOut() {
    	var headers = clearCookies();
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("success");
    }

    private HttpHeaders buildTokenHeader(String accessToken, String refreshToken) {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie(accessToken));
        
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie(refreshToken));

        return headers;
    }
    
    private HttpHeaders clearCookies() {
    	var headers = new HttpHeaders();
    	
    	var domain = properties.getDomain();

        var secured = domain.endsWith(".com");
        
        var sameSite = secured ? SameSite.STRICT.attributeValue() : null;
    	
    	var accessCookie = ResponseCookie.from(JwtTokenFilter.ACCESS_TOKEN_KEY)
    			.maxAge(0)
    			.domain(domain)
    			.secure(secured)
    			.sameSite(sameSite)
    			.path("/")
    			.build();
    	
    	var refreshCookie = ResponseCookie.from(JwtTokenFilter.REFRESH_TOKEN_KEY)
    			.maxAge(0)
    			.domain(domain)
    			.secure(secured)
    			.sameSite(sameSite)
    			.path("/")
    			.httpOnly(true)
    			.build();
    	
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        
        return headers;
    }
    
    private String accessTokenCookie(String token) {
    	var domain = properties.getDomain();

        var secured = domain.endsWith(".com");
        
        var sameSite = secured ? SameSite.STRICT.attributeValue() : null;
        
        var cookie = ResponseCookie.from(JwtTokenFilter.ACCESS_TOKEN_KEY, token)
        .domain(domain)
        .secure(secured)
        .maxAge(Duration.ofDays(30))
        .sameSite(sameSite)
        .path("/")
        .build();
        
        return cookie.toString();
    }
    
    private String refreshTokenCookie(String token) {
    	var domain = properties.getDomain();

        var secured = domain.endsWith(".com");
        
        var sameSite = secured ? SameSite.STRICT.attributeValue() : null;
        
        var cookie = ResponseCookie.from(JwtTokenFilter.REFRESH_TOKEN_KEY, token)
        .domain(domain)
        .secure(secured)
        .maxAge(Duration.ofDays(30))
        .sameSite(sameSite)
        .path("/")
        .httpOnly(true)
        .build();
        
        return cookie.toString();
    }
}
