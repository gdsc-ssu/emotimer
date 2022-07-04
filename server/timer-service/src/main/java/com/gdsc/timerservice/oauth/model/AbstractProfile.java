package com.gdsc.timerservice.oauth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.gdsc.timerservice.oauth.entity.ProviderType;
import lombok.Getter;
import lombok.Setter;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractProfile {

    public abstract String getEmail();
    public abstract String getUsername();

    public abstract ProviderType getProviderType();
}
