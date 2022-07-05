package com.gdsc.timerservice.config.security;

import com.gdsc.timerservice.api.repository.user.UserRefreshTokenRepository;
import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.entity.RoleType;
import com.gdsc.timerservice.oauth.filter.TokenAuthenticationFilter;
import com.gdsc.timerservice.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.gdsc.timerservice.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.gdsc.timerservice.oauth.service.CustomOAuth2UserService;
import com.gdsc.timerservice.oauth.service.IssueRefreshService;
import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Key;

@RequiredArgsConstructor
@EnableWebSecurity(debug = true) // Spring Security 설정을 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final IssueRefreshService issueRefreshService;

    private final CustomOAuth2UserService oAuth2UserService;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider; // config > security > JwtConfig 에서 빈으로 등록했음. 주입받을 수 있음.
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // 일단은 csrf 필터 생성을 막음. 즉 스프링 시큐리티에서 기본적으로 제공하는 csrf 필터를 거치지 않음. 어느 인스턴스에서든 이 서버로 접근 가능하지만, csrf 공격에 취약. 그냥 jwt 토큰으로만 접근관리 하겠음.

        // 메인화면 ("/" 루트 경로) 는 누구나 접근 가능. 이 경로에서 소셜 로그인 진행.
        http.authorizeRequests()
                .antMatchers("/", "/login").permitAll(); // 근데 사실 .anyRequest().permitAll(); 을 아래에서 선언했기 때문에 일단은 따로 설정한 경로를 제외하고는 모든 경로 접근 가능.

        // jwt 사용할 것이기 때문에 세션 만들지 않음.
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // oauth 로그인 설정
        http.oauth2Login()
                .userInfoEndpoint()
                .userService(oAuth2UserService) // oauth2 로그인 성공시 수행될 서비스 등록. 새로운 사용자라면 db 에 User insert 진행. 아니라면 update 로직 수행.
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler()) // 소셜 로그인 성공시, 실행되는 핸들러. jwt 토큰(access token , refresh token) 생성하여 응답
                .failureHandler(oAuth2AuthenticationFailureHandler());

        // URL 별 권한 관리. authorizeRequests 가 선언되어야만 antMatchers 옵션을 사용할 수 있음.
        http.authorizeRequests()
                .antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode()) // "/api/**" USER 권한이 있어야 접근가능.
                .antMatchers("/admin/api/**").hasAnyAuthority(RoleType.ADMIN.getCode()) // "/admin/api/**" ADMIN 권한이 있어야 접근가능.
                .anyRequest().permitAll(); // 위 경로를 제외한 나머지 모든 요청은 접근 허용. .anyRequest.authenticated() 로 한다면 우리 서버로의 모든 접근은 "인증"된 사용자만 들어올 수 있게 됨.

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

//    @Bean
//    public Key generateKey() {
//        return Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes());
//    }

    /**
     * 토큰 필터 설정
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(){
        return new TokenAuthenticationFilter(tokenProvider,issueRefreshService);
    }

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

    /**
     * OAuth 인증 실패 핸들러.
     */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler(){
        return new OAuth2AuthenticationFailureHandler();
    }

}
