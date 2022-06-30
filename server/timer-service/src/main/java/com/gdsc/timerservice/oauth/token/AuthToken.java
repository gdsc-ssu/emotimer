package com.gdsc.timerservice.oauth.token;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {
    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String id, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(id, expiry);
    }

    AuthToken(String email, String role, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(email, role, expiry);
    }

    private String createAuthToken(String email, Date expiry) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createAuthToken(String email, String role, Date expiry) {
        return Jwts.builder()
                .setSubject(email)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 JWT 시그니처임.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰임."); // accessToken 이 만료된 경우, refresh token 을 확인하기 위해 예외를 던짐.
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰임.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 없음.");
        }
        return null;
    }
}
