package com.gdsc.timerservice.config.security;

import com.gdsc.timerservice.api.repository.user.UserRefreshTokenRepository;
import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.gdsc.timerservice.oauth.service.CustomOAuth2UserService;
import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정을 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService oAuth2UserService;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider; // config > security > JwtConfig 에서 빈으로 등록했음. 주입받을 수 있음.
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 메인화면 ("/" 루트 경로) 는 누구나 접근 가능. 이 경로에서 소셜 로그인 진행.
        http.authorizeRequests()
                .antMatchers("/").permitAll();

        // jwt 사용할 것이기 때문에 세션 만들지 않음.
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // oauth 로그인 설정
        http.oauth2Login()
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorization")
//                .authorizationRequestRepository(customOAuth2AuthorizationRequestBasedOnCookieRepository())
//
//                .and()
//                .redirectionEndpoint()
//                .baseUri("/*/oauth2/code/*") // 소셜 로그인이 완료된 후 리다이렉트되는 페이지
//                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService) // oauth2 로그인 성공시 수행될 서비스 등록. 새로운 사용자라면 db 에 User insert 진행. 아니라면 update 로직 수행.
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler()); // 소셜 로그인 성공시, 실행되는 핸들러. jwt 토큰(access token , refresh token) 생성하여 응답
//                .failureHandler(oAuth2AuthenticationFailureHandler());

//        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }


    /**
     * 쿠키 기반 인가 Repository
     * 인가 검증시 사용.
     */
//    @Bean
//    public CustomOAuth2AuthorizationRequestBasedOnCookieRepository customOAuth2AuthorizationRequestBasedOnCookieRepository(){
//        return new CustomOAuth2AuthorizationRequestBasedOnCookieRepository();
//    }

    /**
     * OAuth 인증 성공 핸들러. OAuth 인증 성공시 실행됨.
     * - access token 과 refresh token 을 발급하여 응답헤더에 반환
     */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler(){
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository
        );
    }

}
