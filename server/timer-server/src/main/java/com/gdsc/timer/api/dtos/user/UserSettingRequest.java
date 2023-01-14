package com.gdsc.timerservice.api.dtos.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSettingRequest {
    private Integer timerDuration;
    private Integer restDuration;
    private boolean restAutoStart;
}
