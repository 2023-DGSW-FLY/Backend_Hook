package com.innosync.hook.entity;

import com.innosync.hook.config.SecurityConfig;
import lombok.Builder;

@Builder
public class UserEntity {
    private String provider;
    private String providerId;
    private String loginId;
    private String nickname;
    private String role;

    // 필요한 Getter 메서드들을 추가하세요.

    // UserEntity의 빌더 클래스 정의


    public static UserBuilder builder() {
        return new UserBuilder();
    }

    // UserEntityBuilder 클래스 정의
    public static class UserBuilder {
        private String provider;
        private String providerId;
        private String loginId;
        private String nickname;
        private String role;

        UserBuilder() {
        }

        public UserBuilder provider(String provider) {
            this.provider = provider;
            return this;
        }

        public UserBuilder providerId(String providerId) {
            this.providerId = providerId;
            return this;
        }

        public UserBuilder loginId(String loginId) {
            this.loginId = loginId;
            return this;
        }

        public UserBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserBuilder role(SecurityConfig.UserRole role) {
            this.role = role.name();
            return this;
        }


        public UserEntity build() {

            return new UserEntity(provider, providerId, loginId, nickname, role);
        }
    }
}
