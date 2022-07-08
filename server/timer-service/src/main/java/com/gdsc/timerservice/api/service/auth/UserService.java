package com.gdsc.timerservice.api.service.auth;

import com.gdsc.timerservice.api.dtos.user.UserSettingRequest;
import com.gdsc.timerservice.api.dtos.user.UserSettingResponse;
import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.entity.user.UserSetting;
import com.gdsc.timerservice.api.repository.user.UserRepository;
import com.gdsc.timerservice.api.repository.user.UserSettingRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserSettingRepository userSettingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void update(UserSettingRequest request, User user) {
        UserSetting setting = userSettingRepository
                .findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다." + user.getUserId()));

        modelMapper.map(request, setting);
        userSettingRepository.save(setting);
    }

    public UserSettingResponse querySetting(User user) {
        UserSetting userSetting = userSettingRepository
                .findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다." + user.getUserId()));

        return modelMapper.map(userSetting, UserSettingResponse.class);
    }


    public Page<User> queryAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
