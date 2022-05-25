package com.gdsc.timerservice.api.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USER_REFRESH_TOKEN")
public class UserRefreshToken {

    @Id
    @Column(name = "USER_ID", unique = true)
    @NotNull
    @GeneratedValue
    private String userId;

    @Column(name = "REFRESH_TOKEN")
    @NotNull
    private String refreshToken;

    public UserRefreshToken(@NotNull String userId, @NotNull String refreshToken){
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

}
