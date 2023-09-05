package com.innosync.hook.oauth2;

import com.innosync.hook.entity.User;
import com.innosync.hook.enums.AuthProvider;
import com.innosync.hook.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getOAuth2Id();
    public abstract String getEmail();
    public abstract String getName();
}
