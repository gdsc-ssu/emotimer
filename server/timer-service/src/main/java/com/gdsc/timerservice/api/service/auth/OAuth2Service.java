package com.gdsc.timerservice.api.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.entity.user.UserSetting;
import com.gdsc.timerservice.api.repository.user.UserRefreshTokenRepository;
import com.gdsc.timerservice.api.repository.user.UserRepository;
import com.gdsc.timerservice.api.repository.user.UserSettingRepository;
import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.entity.ProviderType;
import com.gdsc.timerservice.oauth.entity.RoleType;
import com.gdsc.timerservice.oauth.exception.OAuthProviderMissMatchException;
import com.gdsc.timerservice.oauth.model.AbstractOAuthToken;
import com.gdsc.timerservice.oauth.model.AbstractProfile;
import com.gdsc.timerservice.oauth.model.OAuthVendor;
import com.gdsc.timerservice.oauth.model.TokenResponse;
import com.gdsc.timerservice.oauth.token.AuthToken;
import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuth2Service {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    private final UserRepository userRepository;
    private final UserSettingRepository userSettingRepository;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final UserRefreshTokenRepository userRefreshTokenRepository;


    @Transactional
    public TokenResponse generateToken(Long id, String email, RoleType kakao) throws IOException {
        // access token 생성
        Date now = new Date();
        Date accessExpiry = new Date(now.getTime() + appProperties.getAuth().getTokenExpiry());
        AuthToken accessToken = tokenProvider.createAuthToken(id, email, accessExpiry);

        // refresh token 생성
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        Date refreshExpiry = new Date(now.getTime() + refreshTokenExpiry);
        AuthToken refreshToken = tokenProvider.createAuthToken(id, email, refreshExpiry);

        userRefreshTokenRepository.findById(id)
                .ifPresentOrElse(findToken -> {
                    findToken.setRefreshToken(refreshToken.getToken());
                }, () -> {
                    userRefreshTokenRepository.saveNewRefreshToken(id, email, refreshToken.getToken());
                });

        return new TokenResponse(accessToken.getToken(), refreshToken.getToken());
    }

    /**
     * 소셜로그인 사용자들을 강제 회원가입한다.<br/>
     * 이전에 가입된 회원이라면(이메일로 회원 조회시 값 존재) updateUser 로 소셜의 사용자 정보를 업데이트하여 저장한다.<br/>
     * 만약 이전에 가입된 회원이지만(이메일로 회원 조회시 값 존재), 이전에 가입했던 소셜 벤더가 아니라면 OAuthProviderMissMatchException 익셉션을 던진다.<br/>
     */
    @Transactional
    public User socialJoin(String code, String vendor) throws JsonProcessingException {
        var profile = getProfile(code, vendor);
        return findUser(profile);
    }

    /**
     * 중복 가입을 막기 위한 검증을 한다.
     * 현재 소셜 벤더와 로그인을 시도하는 유저의 이메일로 User 테이블의 벤더사를 비교하여 일치 여부를 반환한다.
     *
     *
     * @param profile 현재 소셜 로그인을 시도하는 유저의 프로필 정보
     * @param existingUser 현재 소셜 로그인을 시도하는 유저의 이메일로 User 테이블 조회시 반환된 유저
     * @return 최초 로그인했던 소셜 벤더와 현재 로그인하려는 소셜 벤더의 일치 여부
     */
    private boolean isAlreadyJoined(AbstractProfile profile, User existingUser) {
        return profile.getProviderType() != existingUser.getProviderType();
    }

    private User findUser(AbstractProfile profile) {
        Optional<User> optionalUser = userRepository.findByEmail(profile.getEmail());
        optionalUser.ifPresent(existingUser -> {
            if (isAlreadyJoined(profile, existingUser)) {
                throw new OAuthProviderMissMatchException("이전에 " + existingUser.getEmail() + " 계정으로 로그인하셨던 적이 있습니다.");
            }
        });
        return optionalUser.orElseGet(() -> createOAuthUser(profile.getEmail(), profile.getUsername(), profile.getProviderType()));
    }
    private User createOAuthUser(String email, String username, ProviderType providerType) {
        User user = User.builder()
                .email(email)
                .password(UUID.randomUUID().toString()) // 비밀번호는 일단 그냥 UUID 로 하겠음.
                .username(username)
                .providerType(providerType)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .roleType(RoleType.USER)
                .build();

        User savedUser = userRepository.saveAndFlush(user);
        // 유저 세팅 default 저장
        UserSetting userSetting = UserSetting.builder()
                .user(user)
                .timerDuration(25)
                .restDuration(5)
                .restAutoStart(false)
                .build();

        userSettingRepository.saveAndFlush(userSetting);

        return savedUser;
    }

    private AbstractProfile getProfile(String code, String vendor) throws JsonProcessingException {
        OAuthVendor vendorEnum = OAuthVendor.getVendor(vendor);
        String accessToken = getToken(code, vendorEnum);
        // 2. 액세스 토큰을 요청헤더에 담아 카카오 리소스 서버에 사용자 정보(이메일, 프로필) 요청
        // HttpHeader 생성. 인증타입과 Content-type 명시하기 위함.
        // Http 요청하고 응답받기
        ResponseEntity<String> profileResponse = restTemplate.exchange(
                vendorEnum.getProfileUrl(),
                HttpMethod.POST,
                vendorEnum.createProfileRequest(accessToken),
                String.class
        );

        // 유저 프로필 매핑 완료
        return objectMapper.readValue(profileResponse.getBody(), vendorEnum.getProfileClass());
    }

    private String getToken(String code, OAuthVendor vendorEnum) throws JsonProcessingException {
        // 액세스 토큰 요청하는 Http 요청과 응답
        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                vendorEnum.getTokenUrl(),
                HttpMethod.POST,
                vendorEnum.createAccessTokenRequest(code),
                String.class);

        // 응답받은 json 데이터
        AbstractOAuthToken oauthToken =
                objectMapper.readValue(accessTokenResponse.getBody(), vendorEnum.getTokenClass());
        // 리소스 서버에 접근할 수 있는 액세스 토큰 꺼내기
        return oauthToken.getAccessToken();
    }
}
