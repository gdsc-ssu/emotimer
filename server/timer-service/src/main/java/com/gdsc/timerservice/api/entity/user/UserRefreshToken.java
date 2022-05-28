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
    @GeneratedValue
    @Column(name = "REFRESH_TOKEN_SEQ")
    private Long refreshTokenSeq;

    @Column(name = "EMAIL", unique = true)
    @NotNull
    private String email;

    @Column(name = "REFRESH_TOKEN")
    @NotNull
    private String refreshToken;

    public UserRefreshToken(@NotNull String email, @NotNull String refreshToken){
        this.email = email;
        this.refreshToken = refreshToken;
    }

}
