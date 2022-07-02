package com.gdsc.timerservice.api.entity.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USER_REFRESH_TOKEN")
@Builder @Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class UserRefreshToken {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL", unique = true)
    @NotNull
    private String email;

    @Column(name = "REFRESH_TOKEN")
    @NotNull
    private String refreshToken;


}
