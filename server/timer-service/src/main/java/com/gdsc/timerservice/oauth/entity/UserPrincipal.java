package com.gdsc.timerservice.oauth.entity;

import com.gdsc.timerservice.api.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class UserPrincipal extends org.springframework.security.core.userdetails.User implements OAuth2User {

    private User user;
    private Map<String, Object> oauthUserAttributes;

    public UserPrincipal(User user, Map<String, Object> oauthUserAttributes) {
        super(user.getUsername(), user.getPassword(), convertToAuthorities(user));
        this.user = user;
        this.oauthUserAttributes = oauthUserAttributes;
    }

    private static Set<SimpleGrantedAuthority> convertToAuthorities(User user) {
        return Stream.of(user.getRoleType().getCode())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

//    public UserPrincipal(User user, Map<String, Object> oauthUserAttributes) {
//        this.user = user;
//        this.oauthUserAttributes = oauthUserAttributes;
//    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getName() { // 우리 서비스에서는 이메일로.
        return user.getEmail();
    }
}
