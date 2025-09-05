package org.imures.cashregister.configuration;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY; // for testing purpose only, change after!

    @Value("${application.security.jwt.refresh-secret-key}")
    private String REFRESH_SECRET_KEY;

    @Value("${application.security.jwt.expiration-time}")
    private long EXPIRATION_TIME;

    @Value("${application.security.jwt.refresh-expiration-time}")
    private long REFRESH_EXPIRATION_TIME;


    public boolean validateJwtToken(String authToken){
        return validateJwtToken(authToken, getSingInKey());
    }

    public boolean validateJwtRefreshToken(String authToken){
        return validateJwtToken(authToken, getRefreshKey());
    }

    private boolean validateJwtToken(String authToken, SecretKey secretKey) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage());
        } catch (JwtException e) {
            log.warn("JWT token validation failed: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }

//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }

    public String getSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String getRefreshSubject(String refreshToken) {
        return extractAllClaims(refreshToken, getRefreshKey()).getSubject();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, getSingInKey());
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, SecretKey signingKey) {
        return Jwts
                .parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return generateToken(userDetails, new HashMap<>(), getRefreshKey(), REFRESH_EXPIRATION_TIME);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return generateToken(userDetails, extraClaims, getSingInKey(), EXPIRATION_TIME);
    }

    private String generateToken(UserDetails userDetails, Map<String, Object> extraClaims, Key singKey, long EXPIRATION_TIME) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(singKey)
                .compact();
    }

    private SecretKey getSingInKey() {
        return getKey(SECRET_KEY);
    }

    private SecretKey getRefreshKey() {
        return getKey(REFRESH_SECRET_KEY);
    }

    private SecretKey getKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}