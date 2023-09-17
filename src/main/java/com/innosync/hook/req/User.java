package com.innosync.hook.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_account")
    private String userAccount;

    private String password;

    @Column
    private String user_name;

    @Column
    private String user_info;

    @Column
    private String email;

    @Column
    private String github_url;

    @Column
    private String portfolio_url;


    @Enumerated(EnumType.STRING)
    @Column(name = "user_Role")
    private UserRole userRole;

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

}
