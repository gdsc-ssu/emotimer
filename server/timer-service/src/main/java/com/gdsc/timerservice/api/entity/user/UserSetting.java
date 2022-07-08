package com.gdsc.timerservice.api.entity.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer timerDuration = 50; // 메인 화면에 표시될 타이머 진행시킬 시간

    private Integer restDuration = 5;

    private boolean restAutoStart = false; // 휴식 시간 종료 후 타이머 자동 시작 여부
}
