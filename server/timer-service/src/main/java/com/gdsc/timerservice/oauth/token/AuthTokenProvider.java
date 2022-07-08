package com.gdsc.timerservice.oauth.token;

import com.gdsc.timerservice.api.repository.user.UserRepository;
import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.entity.UserPrincipal;
import com.gdsc.timerservice.oauth.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenProvider {

	private static final String AUTHORITIES_KEY = "role";
	private final AppProperties appProperties;
	private final UserRepository userRepository;

	public AuthToken createAuthToken(String id, String email, Date expiry) {
		return new AuthToken(appProperties, id, email, expiry);
	}

	public AuthToken convertAuthToken(String token) {
		return AuthToken.createNewOne(appProperties, token);
	}

	public Authentication getAuthentication(AuthToken authToken) {
		if (authToken.validate()) {

			Claims claims = authToken.getTokenClaims();
			Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

			log.debug("claims subject := [{}]", claims.getSubject());
			var user = findUser(claims);
			User principal = new UserPrincipal(user, null);

			return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
		} else {
			throw new TokenValidFailedException();
		}
	}

	private com.gdsc.timerservice.api.entity.user.User findUser(Claims claims) {
		return userRepository.findById(Long.parseLong(claims.get("id").toString()))
			.orElseThrow(() -> new UsernameNotFoundException(claims.get("id") + "에 해당하는 유저를 찾을 수 없습니다."));
	}

}
