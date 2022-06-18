package com.gdsc.timerservice.api.controller.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.timerservice.api.service.OAuth2Service;
import com.gdsc.timerservice.oauth.entity.ProviderType;
import com.gdsc.timerservice.oauth.entity.RoleType;
import com.gdsc.timerservice.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.gdsc.timerservice.oauth.model.KakaoOAuthToken;
import com.gdsc.timerservice.oauth.model.KakaoProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuth2Service oAuth2Service;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    /**
     * 사용자의 카카오 로그인 요청 후, 로그인한 다음 리다이렉트된 경로.<br/><br/>
     * 카카오로 총 두번의 HTTP 요청으로 사용자 정보(이메일, 이름) 를 알아온다. <br/>
     * 최종적으로 반환받은 사용자 정보로 강제 회원가입 절차를 진행한다. <br/><br/>
     * 1. 로그인 성공 후 받은 쿼리파리미터의 code 를 이용해 카카오 인증 서버에 access token 요청<br/>
     * 2. 받은 access token 을 가지고 카카오 리소스 서버에 사용자 정보 요청
     */
    @GetMapping("/callback/kakao")
    public String codeFromKakao(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws JsonProcessingException {
        System.out.println("code: " + code);

        // 1. 받은 코드를 요청헤더 담아 카카오 서버에 액세스 토큰 요청을 보낸다 (POST 방식)
        RestTemplate rt = new RestTemplate();

        // HttpHeader 생성. Content-type 명시하기 위함.
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // HttpBody 생성.
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "116cc9d3778c5d9578487bce8f08e024");
        // TODO 클라이언트 id 와 secret .proterties 파일에서 불러오기
        params.add("client_secret", "aTnSYwnw2qCJ0zZ9Br74HUUoWS0kMVwy");
        params.add("redirect_uri", "http://localhost:8080/callback/kakao");
        params.add("code", code);

        // HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
        // HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoRequest1 = new HttpEntity<>(params, header);

        // 액세스 토큰 요청하는 Http 요청과 응답
        ResponseEntity<String> response1 = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoRequest1, String.class);

        // 응답받은 json 데이터
        System.out.println("==response==" + response1);
        ObjectMapper mapper = new ObjectMapper();
        KakaoOAuthToken kakaoOAuthToken = mapper.readValue(response1.getBody(), KakaoOAuthToken.class);

        // 리소스 서버에 접근할 수 있는 액세스 토큰 꺼내기
        String access_token = kakaoOAuthToken.getAccessToken();
        System.out.println("액세스 토큰: " + access_token);

        // 2. 액세스 토큰을 요청헤더에 담아 카카오 리소스 서버에 사용자 정보(이메일, 프로필) 요청
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 생성. 인증타입과 Content-type 명시하기 위함.
        HttpHeaders header2 = new HttpHeaders();
        header2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header2.setBearerAuth(access_token); // 인증방식은 Bearer. 이 키에 access token 을 할당.

        // Http 헤더 포함하여 하나의 Http 요청 만들기
        HttpEntity<MultiValueMap<String, String>> kakaoRequest2 = new HttpEntity<>(header2);

        // Http 요청하고 응답받기
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoRequest2,
                String.class
        );

        System.out.println("== 사용자 프로필 출력 ==: " + response2);

        // 유저 프로필 매핑 완료
        KakaoProfile kakaoProfile = mapper.readValue(response2.getBody(), KakaoProfile.class);
        System.out.println(kakaoProfile);
        String email = kakaoProfile.getKakaoAccount().getEmail(); // 이메일 추출
        String username = kakaoProfile.getKakaoAccount().getProfile().getNickname();// TODO 사용자 이름 추출하여 DB 에 넘겨주어야함. 근데 그냥 유저 모델 객체를 만들자.
        System.out.println("추출한 이메일: " + email);

        // 강제 회원가입 시작
        oAuth2Service.join(email,username, ProviderType.KAKAO);

        // 액세스 토큰과 리프레시 토큰 발급해서 HttpResponse 에 담아 클라이언트 응답으로 반환
        oAuth2Service.generateToken(response, email, RoleType.USER);

        return "kakao success";
    }

    @GetMapping("/callback/apple")
    public String codeFromApple(){

        return null;
    }

}
