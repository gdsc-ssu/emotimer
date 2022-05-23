package com.example.authservice.oauth.entity;

import com.example.authservice.api.entity.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {

    private User user;
    private Map<String, Object> oauthUserAttributes;

    public UserPrincipal(User user, Map<String, Object> oauthUserAttributes){
        this.user = user;
        this.oauthUserAttributes = oauthUserAttributes;
    }


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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
