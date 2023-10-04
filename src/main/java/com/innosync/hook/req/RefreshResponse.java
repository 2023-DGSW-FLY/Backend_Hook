package com.innosync.hook.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshResponse {
    private String accessToken;

    public RefreshResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
