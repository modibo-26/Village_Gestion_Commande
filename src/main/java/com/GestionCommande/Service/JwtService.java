package com.GestionCommande.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final String SECRET_KEY = "bW9uU3VwZXJTZWNyZXRLZXlRdWlFc3RBc2V6TG9uZ3VlUG91ckhTMjU2";
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // En millisecondes


    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + this.EXPIRATION))
                .signWith(this.getSigningKey())
                .compact();
        return token;
    }

    public String extractUsername(String token) {
        String username = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return username;
    }

    public boolean isTokenValid(String token, String username) {
        String extract = extractUsername(token);
        boolean bool = extract.equals(username);
        return bool;
    }
}
