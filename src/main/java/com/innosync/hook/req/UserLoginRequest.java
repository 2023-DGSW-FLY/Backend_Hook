package com.innosync.hook.req;


import lombok.Getter;

@Getter
public class UserLoginRequest {

    private String userAccount;
    private String password;
}
