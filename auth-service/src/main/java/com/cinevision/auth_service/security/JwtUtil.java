package com.cinevision.auth_service.security;

import io.jsonwebtoken.*;
// Add this for generating the secret key from bytes
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets; // For safe byte conversion
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key signingKey;
    private final long jwtExpirationMs; // 💡 Re-declared as a field

    public JwtUtil(
            @Value("${jwt.secret:secret-key}") String jwtSecret,
            @Value("${jwt.expiration:3600000}") long jwtExpirationMs // Inject the expiration time
    ) {
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        this.jwtExpirationMs = jwtExpirationMs;
    }

    // Generate JWT token
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                // 3. The field is now accessible (resolves "Cannot resolve symbol 'jwtExpirationMs'")
                .setExpiration(new Date(System.currentTimeMillis() + this.jwtExpirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Get username from token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // It is highly recommended to log the exception here.
            System.err.println("JWT Validation Error: " + e.getMessage());
            return false;
        }
    }
}