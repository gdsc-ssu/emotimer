package com.gdsc.timerservice.oauth.token;

import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.entity.RoleType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Getter
public class AuthToken {
    private String token;

    private final AppProperties appProperties;

    public AuthToken(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public static AuthToken createNewOne(AppProperties appProperties, String token) {
        AuthToken authToken = new AuthToken(appProperties);
        authToken.token = token;
        return authToken;
    }

    public AuthToken(AppProperties appProperties, String uuid, String email, Date expiry) {
        this.appProperties = appProperties;
        this.token = createAuthToken(uuid, email, RoleType.USER, expiry);
    }


    private String createAuthToken(String uuid, String email, RoleType role, Date expiry) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("id", uuid);
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes()), SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes()))
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
