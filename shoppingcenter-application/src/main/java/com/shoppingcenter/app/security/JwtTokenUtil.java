package com.shoppingcenter.app.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    @Autowired
    private Key key;

    public String generateAccessToken(String subject) {
        var validityInMilliseconds = 3600000L; // 1 hour

        return generateToken(subject, validityInMilliseconds);
    }

    public String generateRefreshToken(String subject) {
        var validityInMilliseconds = 2592000000L; // 30 days

        return generateToken(subject, validityInMilliseconds);
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(String subject, long validityInMilliseconds) {
        var claims = Jwts.claims().setSubject(subject);

        var now = new Date();
        var expired = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
