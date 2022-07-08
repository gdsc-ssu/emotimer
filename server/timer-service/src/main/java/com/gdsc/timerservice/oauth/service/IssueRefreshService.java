package com.gdsc.timerservice.oauth.service;

import com.gdsc.timerservice.api.entity.user.UserRefreshToken;
import com.gdsc.timerservice.api.repository.user.UserRefreshTokenRepository;
import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.model.TokenResponse;
import com.gdsc.timerservice.oauth.token.AuthToken;
import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import com.gdsc.timerservice.util.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueRefreshService {

	private final AppProperties appProperties;
	private final AuthTokenProvider tokenProvider;
	private final UserRefreshTokenRepository userRefreshTokenRepository;

	public Optional<TokenResponse> refreshToken(HttpServletRequest request) {
		log.info("리프레시 토큰 발급 준비중...");

		// refresh token 유효성 확인
		String refreshToken = HeaderUtil.getRefreshToken(request);
		AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);
		Claims tokenClaims = authRefreshToken.getTokenClaims();

		String email = tokenClaims.getSubject(); // 유저 이메일
		if (!authRefreshToken.validate()) {
			log.info("유효하지 않은 리프레시 토큰임.");
            return Optional.empty();
		}

		// email 과 refresh token 으로 DB 확인. 있다면 새로운 토큰 생성하여 응답
		UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByIdAndRefreshToken(Long.parseLong(tokenClaims.get("id").toString()), refreshToken);
		if (userRefreshToken == null) {
			log.info("해당하는 리프레시 토큰이 DB에 존재하지 않음");
            return Optional.empty();
		}
		Date now = new Date();
		// 새로운 토큰 생성
		AuthToken accessToken = tokenProvider.createAuthToken("1L", // TODO 서연님께 하드코딩 된 내용 물어보기
			email,
			new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));
		log.info("새로운 액세스 토큰 헤더에 탑재 완료!");
        return Optional.of(new TokenResponse(accessToken.getToken(), userRefreshToken.getRefreshToken()));
	}
}
