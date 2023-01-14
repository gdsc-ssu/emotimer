package com.gdsc.timerservice.api.dtos.user;

import com.gdsc.timerservice.api.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserResponse {
    private String username;
    private String email;

    public GetUserResponse(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
