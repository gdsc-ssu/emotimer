package com.gdsc.timerservice.oauth.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuthRetrieverFactory {

    private final OAuthRetriever kakaoOAuthRetriever;
    private final OAuthRetriever appleOAuthRetriever;

    public OAuthRetriever getOAuthRetrieverByVendor(String vendorName) {
        switch (vendorName) {
            case "kakao":
                return kakaoOAuthRetriever;
            case "apple":
                return appleOAuthRetriever;
            default:
                throw new RuntimeException("[" + vendorName + "] is not supported");
        }
    }


}
