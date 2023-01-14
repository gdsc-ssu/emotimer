package com.gdsc.timerservice.oauth.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.mockito.Mockito.any;
import static org.springframework.http.HttpMethod.GET;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokenAuthenticationFilterTest {

    @LocalServerPort
    private int port;


    @InjectMocks
    TokenAuthenticationFilter filter;


    public URI uri(String path) {
        try {
            return new URI(String.format("http://localhost:%d%s", port, path));
        } catch (Exception ex) {
            throw new IllegalArgumentException();
        }
    }

    private RestTemplate getClient() {
        return new RestTemplate();
    }

    //TODO
    // 1. header에 아무것도 안 준 경우
    // 2. header에 토큰을 준 경우
    //      a. header에 올바른 토큰 준 경우
    //      b. header에 올바르지 않은 토큰을 준 경우

    @DisplayName("header에 아무것도 안 준 경우")
    @Test
    void test1() throws Exception {
        RestTemplate client = getClient();

        Mockito.doAnswer(invocation -> {
            System.out.println("invocation = " + invocation);
            return null;
        })
                .when(filter)
                .doFilterInternal(any(), any(), any());
        client.exchange(
                uri("/"),
                GET,
                null,
                String.class
        );

    }

//    @DisplayName("header에 아무것도 안 준 경우 테스트")
//    @Test
//    void test1() {
//        RestTemplate client = getClient();
//        client.exchange(
//                uri("/"),
//                GET,
//                null,
//                String.class
//        );
//        assertNull(SecurityContextHolder.getContext().getAuthentication());
//    }
//
//
//    @DisplayName("header에 뭔가 준 경우")
//    @Test
//    void test2() {
//        AuthToken mockAuthToken = mock(AuthToken.class);
//        when(tokenProvider.getAuthentication(any(AuthToken.class)))
//                .thenReturn(new UsernamePasswordAuthenticationToken("test_principal", "", null));
//
//        when(mockAuthToken.validate())
//                .thenReturn(true);
//
//        when(tokenProvider.convertAuthToken(null))
//                .thenReturn(mockAuthToken);
//
//        RestTemplate client = getClient();
//        client.exchange(
//                uri("/"),
//                GET,
//                null,
//                String.class
//        );
//
//        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
//    }


}