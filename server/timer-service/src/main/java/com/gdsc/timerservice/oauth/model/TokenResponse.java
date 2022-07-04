package com.gdsc.timerservice.oauth.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class TokenResponse implements Serializable {
    private final String accessToken;
    private final String refreshToken;
}
