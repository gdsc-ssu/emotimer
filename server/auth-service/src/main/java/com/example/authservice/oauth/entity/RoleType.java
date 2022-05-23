package com.example.authservice.oauth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum RoleType {
    USER("ROLE_USER", "일반 사용자 권한"), // 스프링 시큐리티에서는 권한코드에 "ROLE_" prefix 가 붙어있어야 인식가능
    ADMIN("ROLE_ADMIN", "관리자 권한"),
    GUEST("ROLE_GUEST", "게스트 권한");

    private final String code;
    private final String displayName;

    public static RoleType of(String code){
        return Arrays.stream(RoleType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(GUEST);
    }

}
