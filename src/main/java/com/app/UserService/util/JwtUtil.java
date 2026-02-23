package com.app.UserService.util;

import java.security.Key;
import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String secret =
            "mysecretkeymysecretkeymysecretkey"; // 32+ chars

    private static final Key key =
            Keys.hmacShaKeyFor(secret.getBytes());

    // ✅ Generate Token
    public String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", String.join(",", roles))
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extract Username (FIXED)
    public String extractUserName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // ✅ Extract Roles (IMPORTANT)
    public String extractRoles(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", String.class);
    }

    // ✅ Validate Token
    public boolean validateToken(String token, String username) {
        String tokenUsername = extractUserName(token);
        return tokenUsername.equals(username);
    }
}