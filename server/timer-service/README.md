# 인증 처리는 OAuth2 소셜 로그인

# 권한 처리를 위한 jwt 토큰 발급
## Access Token
![img.png](img.png)

## Refresh Token 
![img_1.png](img_1.png)
사용자(프론트엔드)에서 Access Token의 Payload를 통해 유효기간을 알 수 있다. 
따라서 프론트엔드 단에서 API 요청 전에 토큰이 만료됐다면 바로 재발급 요청을 할 수도 있다.

즉, `액세스토큰 만료` && `리프레시토큰 만료되지않음` 일때 "/refresh" 를 요청하면 새로운 액세스 토큰을 발급해준다.

`액세스토큰 만료` && `리프레시토큰 만료` 라면 아무 토큰도 보내지 않게 한다. 그러면 자동으로 "/login" 으로 리다이렉트 됨.

이모지 -> Enum -> Category