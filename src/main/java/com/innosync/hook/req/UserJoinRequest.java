package com.innosync.hook.req;
import lombok.Getter;

@Getter

public class UserJoinRequest {

    private String userAccount;
    private String password;
    private String userName;
    private String email;
    private String user_info;
    private String github_url;
    private String v;
    private int age;


}
