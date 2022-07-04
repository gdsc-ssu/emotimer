package com.gdsc.timerservice.oauth.service;

import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.entity.user.UserSetting;
import com.gdsc.timerservice.api.repository.user.UserRepository;
import com.gdsc.timerservice.api.repository.user.UserSettingRepository;
import com.gdsc.timerservice.oauth.entity.ProviderType;
import com.gdsc.timerservice.oauth.entity.RoleType;
import com.gdsc.timerservice.oauth.entity.UserPrincipal;
import com.gdsc.timerservice.oauth.exception.OAuthProviderMissMatchException;
import com.gdsc.timerservice.oauth.info.OAuth2UserInfo;
import com.gdsc.timerservice.oauth.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체.
 * 리소스 서버에서 가져온 사용자 정보로 추가적인 행동을 명시할 수 있음.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final UserSettingRepository userSettingRepository;

    // 방금 로그인 완료한 사용자가 이미 회원가입이 되어있는 사람인지 체크. DB 뒤짐.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try{
            return this.updateOrCreate(userRequest, user); // 이 함수에서 DB 뒤짐.
        }catch (AuthenticationException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User updateOrCreate(OAuth2UserRequest userRequest, OAuth2User user){
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase()); // 벤더 이름 뽑아냄.
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType,user.getAttributes());

        User savedUser = findUser(providerType, userInfo);
        return new UserPrincipal(savedUser, user.getAttributes());
    }

    private User findUser(ProviderType providerType, OAuth2UserInfo userInfo) {
        Optional<User> optionalUser = userRepository.findByEmail(userInfo.getEmail());
        optionalUser.ifPresent(savedUser -> {
            if (providerType != savedUser.getProviderType()){ // 가입된 회원이긴 한데 이전에 로그인한 소셜과 다른 소셜로 로그인 한 경우.
                throw new OAuthProviderMissMatchException(
                        "이전에 " + providerType + " 계정으로 로그인하셨던 적이 있습니다. "  + providerType + " 으로 로그인해주세요."
                );
            }
            updateUser(savedUser, userInfo); // 그냥 유저 정보 업데이트 된거 있으면 업데이트만!
        });
        return optionalUser.orElse(createUser(userInfo, providerType));
    }

    // 새로운 유저 생성하여 DB 에 저장
    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType){
        User user = User.builder()
                .email(userInfo.getEmail())
                .password(UUID.randomUUID().toString()) // 비밀번호는 걍 uuid 로 하겠음.
                .username(userInfo.getUserName())
                .providerType(providerType)
                .roleType(RoleType.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        User savedUser = userRepository.saveAndFlush(user);
        // default 유저 세팅 저장
        UserSetting userSetting = UserSetting.builder()
                .user(user)
                .timerDuration(25)
                .restDuration(5)
                .restAutoStart(false)
                .build();
        userSettingRepository.saveAndFlush(userSetting);

        return savedUser;
    }

    // 기존 유저. 업데이트 된거 있으면 업뎃.
    private User updateUser(User user, OAuth2UserInfo userInfo){
        // 유저 이름이 기존에 우리 DB 에 저장된 이름과 달라졌다면 업뎃.
        if(userInfo.getUserName() != null && ! ( userInfo.getUserName().equals(userInfo.getUserName()) )){
            user.setUsername(userInfo.getUserName());
        }
        // 유저의 이미지 url 이 우리 DB 에 저장된것과 달려졌다면 업뎃.
        if(userInfo.getImageUrl() != null && ! ( userInfo.getImageUrl().equals(userInfo.getImageUrl()) )){
            user.setImageUrl(userInfo.getImageUrl());
        }
        return user;
    }


}
