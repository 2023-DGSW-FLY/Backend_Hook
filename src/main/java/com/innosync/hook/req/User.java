package com.innosync.hook.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(name = "user_account")
    @JsonProperty("userAccount")
    private String userAccount;

    @NotNull
    @JsonIgnore
    private String password;

    @NotNull
    @Column
    @JsonProperty("user_name")
    private String user_name;

    @Column
    @JsonProperty("user_info")
    private String user_info;

    @Column
    @JsonProperty("email")
    private String email;

    @Column
    @JsonProperty("github_url")
    private String github_url;

    @Column
    @JsonProperty("portfolio_url")
    private String portfolio_url;

    @Column
    @JsonProperty("imageUrl")
    private String imageUrl;


    @Enumerated(EnumType.STRING)
    @Column(name = "user_Role")
    private UserRole userRole;

    @Column
    private String firebaseToken;

    @Builder
    public User(String userAccount, String password, String user_name, String email, String user_info, String github_url, String portfolio_url) {
        this.userAccount = userAccount;
        this.password = password;
        this.user_name = user_name;
        this.email = email;
        this.user_info = user_info;
        this.github_url = github_url;
        this.portfolio_url = portfolio_url;
        this.userRole = UserRole.USER;
    }

    public void addImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void addFirebaseToken(String firebaseToken){
        this.firebaseToken=firebaseToken;
        //System.out.println("토큰 들어옴 | 들어온 파베토큰 | 바뀐 파베토큰 " + firebaseToken + " " + this.firebaseToken);
    }
    public void fixUserData(String userAccount, String user_name, String email, String user_info, String github_url, String portfolio_url) {
        this.userAccount = userAccount;
        this.user_name = user_name;
        this.email = email;
        this.user_info = user_info;
        this.github_url = github_url;
        this.portfolio_url = portfolio_url;
    }
}
