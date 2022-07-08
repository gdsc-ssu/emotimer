package com.gdsc.timerservice.api.entity.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder @Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class UserRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @NotNull
    private String email;

    @NotNull
    private String refreshToken;


}
