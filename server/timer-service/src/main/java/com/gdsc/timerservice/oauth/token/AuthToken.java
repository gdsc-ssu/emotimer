package com.gdsc.timerservice.oauth.token;

import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.entity.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AuthToken {

	private String token;

	private final AppProperties appProperties;

	//AuthToken(Long id, String email, Date expiry) {
	//    this.token = createAuthToken(id, email, RoleType.USER, expiry);
	//    this.appProperties = appProperties;
	//}

//    AuthToken(String token) {
//        this.token = token;
//    }

	public AuthToken(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public static AuthToken createNewOne(AppProperties appProperties, String token) {
		AuthToken authToken = new AuthToken(appProperties);
		authToken.token = token;
		return authToken;
	}

	public AuthToken(AppProperties appProperties, String id, String email, Date expiry) {
		this.appProperties = appProperties;
		this.token = createAuthToken(id, email, RoleType.USER, expiry);
	}


	private String createAuthToken(String id, String email, RoleType role, Date expiry) {
		Claims claims = Jwts.claims().setSubject(email);
		claims.put("id", id);
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
			log.info("???????????? ?????? JWT ???????????????.");
		} catch (ExpiredJwtException e) {
			log.info("????????? JWT ?????????."); // accessToken ??? ????????? ??????, refresh token ??? ???????????? ?????? ????????? ??????.
			throw e;
		} catch (UnsupportedJwtException e) {
			log.info("???????????? ?????? JWT ?????????.");
		} catch (IllegalArgumentException e) {
			log.info("JWT ????????? ??????.");
		}
		return null;
	}
}
