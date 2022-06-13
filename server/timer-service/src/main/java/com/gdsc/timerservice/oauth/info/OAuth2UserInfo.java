package com.gdsc.timerservice.oauth.info;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes(){
        return attributes;
    }

    public abstract String getId();
    public abstract String getEmail();
    public abstract String getUserName();

    public abstract String getImageUrl();

}
