package com.gdsc.timerservice.oauth.token;

import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenProvider {
    private static final String AUTHORITIES_KEY = "role";
    private final AppProperties appProperties;

    public AuthToken createAuthToken(Long id, String email, Date expiry) {
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
            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }

}
