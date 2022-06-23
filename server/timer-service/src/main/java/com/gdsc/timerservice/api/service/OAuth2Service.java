package com.gdsc.timerservice.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.entity.user.UserRefreshToken;
import com.gdsc.timerservice.api.repository.user.UserRefreshTokenRepository;
import com.gdsc.timerservice.api.repository.user.UserRepository;
import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.entity.ProviderType;
import com.gdsc.timerservice.oauth.entity.RoleType;
import com.gdsc.timerservice.oauth.exception.OAuthProviderMissMatchException;
import com.gdsc.timerservice.oauth.model.AbstractOAuthToken;
import com.gdsc.timerservice.oauth.model.AbstractProfile;
import com.gdsc.timerservice.oauth.model.OAuthVendor;
import com.gdsc.timerservice.oauth.token.AuthToken;
import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;


    private final UserRepository userRepository;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    /**
     * 소셜로그인 사용자들을 강제 회원가입한다.<br/>
     * 이전에 가입된 회원이라면(이메일로 회원 조회시 값 존재) updateUser 로 소셜의 사용자 정보를 업데이트하여 저장한다.<br/>
     * 만약 이전에 가입된 회원이지만(이메일로 회원 조회시 값 존재), 이전에 가입했던 소셜 벤더가 아니라면 OAuthProviderMissMatchException 익셉션을 던진다.<br/>
     */
    public User socialJoin(String code, String vendor) throws JsonProcessingException {
        AbstractProfile profile = getProfile(code, vendor);
        User existingUser = userRepository.findByEmail(profile.getEmail()); // 이미 가입된 유저(이메일)인지 체크하기 위함.

        if (existingUser != null) { // 이미 가입된 유저(이메일)라면
            if (profile.getProviderType() != existingUser.getProviderType()) { // 이미 가입된 유저(이메일)인데 다른 소셜벤더로 로그인한 경우
                throw new OAuthProviderMissMatchException(
                        "이전에 " + profile.getProviderType() + " 계정으로 로그인하셨던 적이 있습니다." + profile.getProviderType() + " 로그인으로 로그인하시겠습니까?"
                );
            }

            // TODO 유저 정보 업데이트시 현재 로그인한 사용자의 이메일만 주면 안되고, 소셜로그인 후 카카오정보서버로부터 받은 모든 정보(이미지Url, 이미지, 닉네임 등)를 담은 객체를 주어야 한다.
            existingUser = updateOAuthInfo(profile.getEmail(), existingUser);
        } else { // 처음 로그인하는 회원인경우, 강제 회원가입 진행
            existingUser = createOAuthUser(profile.getEmail(), profile.getUsername(), profile.getProviderType());
        }

        return existingUser;
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

        return userRepository.save(user);
    }

    private User updateOAuthInfo(String email, User user) {
        // TODO oauth 사용자 정보(특히 프로필 url, 닉네임 등이 업데이트 된 경우 우리 서비스에서도 업데이트해줌
        return user;
    }

    // 토큰 생성
    public void generateToken(HttpServletResponse response, String email, RoleType kakao) {
        // access token 생성
        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(email, String.valueOf(kakao), new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));

        // refresh token 생성
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(email, new Date(now.getTime() + refreshTokenExpiry));

        // refresh token DB 에 저장
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByEmail(email);

        if (userRefreshToken != null) { // 기존에 리프레시 토큰이 있었다면, 새롭게 생성한 refresh token 으로 덮어쓰기.
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        } else { // 기존에 리프레시 토큰을 한번도 발급받지 않은 유저, 즉 현재 처음으로 소셜로그인하는 경우, refresh token 신규 저장.
            userRefreshToken = new UserRefreshToken(email, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        }
        response.setHeader("access_token", accessToken.getToken());
        response.setHeader("refresh_token", refreshToken.getToken());

    }

    public AbstractProfile getProfile(String code, String vendor) throws JsonProcessingException {
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
