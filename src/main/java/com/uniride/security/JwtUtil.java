package com.uniride.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un JWT con email, rol y opcionalmente nombre o ID.
     */
    public String generateToken(Long userId, String email, String rol) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(email) // identifica al usuario
                .claim("id", userId)
                .claim("rol", rol) // PASAJERO o CONDUCTOR
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el correo electrónico (subject) del token.
     */
    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Extrae el rol (PASAJERO o CONDUCTOR) del token.
     */
    public String getRolFromToken(String token) {
        return parseClaims(token).get("rol", String.class);
    }

    /**
     * Extrae el ID del usuario si fue incluido en el token.
     */
    public Long getUserIdFromToken(String token) {
        Object idClaim = parseClaims(token).get("id");
        return idClaim != null ? Long.valueOf(idClaim.toString()) : null;
    }

    /**
     * Valida que el token sea correcto y no esté expirado.
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Token no soportado: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Token malformado: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Firma JWT inválida: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Token vacío o inválido: " + e.getMessage());
        }
        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
