package com.innosync.hook.oauth2;

import com.innosync.hook.enums.AuthProvider;

import java.util.Map;


public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        switch (authProvider) {
            case KAKAO: return new KakaoOAuth2User(attributes);

            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}