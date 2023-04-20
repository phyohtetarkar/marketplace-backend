package com.shoppingcenter.app.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

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
        	 String errorBody = e.getMessage();
            if (e instanceof ExpiredJwtException) {
                errorBody = "token-expired";
            }
            
            var out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			out.print(errorBody);
			out.flush();
			return;
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
