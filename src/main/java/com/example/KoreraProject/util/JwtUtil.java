package com.example.KoreraProject.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "secret";
    public String generateToken(String username,Role role, long expire) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role",role.name())
                .withExpiresAt(new Date(System.currentTimeMillis() + expire))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
