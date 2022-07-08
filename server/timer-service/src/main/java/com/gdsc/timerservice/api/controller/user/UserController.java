package com.gdsc.timerservice.api.controller.user;

import com.gdsc.timerservice.api.dtos.user.GetUserResponse;
import com.gdsc.timerservice.api.dtos.user.UserSettingRequest;
import com.gdsc.timerservice.api.dtos.user.UserSettingResponse;
import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.service.auth.UserService;
import com.gdsc.timerservice.common.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping("/view") // 메인 페이지. 소셜 로그인할 수 있는 링크들 있음.
    public String hello(@CurrentUser User user) {
        return "index";
    }

    /**
     * 모든 유저 조회
     *
     * @param pageable
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<Page<GetUserResponse>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.queryAllUser(pageable).map(GetUserResponse::new));
    }



    /**
     * 해당 유저
     *
     * @param user
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<GetUserResponse> getMyProfile(@CurrentUser User user) {
        return ResponseEntity.ok(new GetUserResponse(user));
    }


    /**
     * 해당 유저 설정 조회
     *
     * @param user
     * @return
     */
    @GetMapping("/me/setting")
    public ResponseEntity<UserSettingResponse> getSetting(@CurrentUser User user) {
        UserSettingResponse response = userService.querySetting(user);
        return ResponseEntity.ok(response);
    }

    /**
     * 해당 유저 설정 수정
     *
     * @param request
     * @param user
     */
    @PatchMapping("/me/setting")
    public ResponseEntity<String> updateSetting(@RequestBody UserSettingRequest request, @CurrentUser User user) {
        userService.update(request, user);
        return ResponseEntity.noContent().build();
    }

}
