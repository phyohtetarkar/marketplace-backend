package com.shoppingcenter.app.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String ACCESS_TOKEN_KEY = "accessToken";

    public static final String REFRESH_TOKEN_KEY = "refreshToken";

    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService userDetailsService;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = resolveToken(request);

        try {
            if (StringUtils.hasText(accessToken)) {
                var claims = jwtTokenUtil.parseToken(accessToken);

                var userDetails = userDetailsService.loadUserByUsername(claims.getSubject());

                var authentication = new UsernamePasswordAuthenticationToken(userDetails, "",
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                throw new ApplicationException(ErrorCodes.UNAUTHORIZED, "token-expired");
            }
            throw new ApplicationException(ErrorCodes.UNAUTHORIZED, e.getMessage());
        }

        filterChain.doFilter(request, response);

    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        // } else {
        // var cookies = request.getCookies();

        // if (cookies == null) {
        // return null;
        // }

        // for (var cookie : cookies) {
        // if (ACCESS_TOKEN_KEY.equals(cookie.getName())) {
        // return cookie.getValue();
        // }
        // }
        // }

        return null;
    }

}
