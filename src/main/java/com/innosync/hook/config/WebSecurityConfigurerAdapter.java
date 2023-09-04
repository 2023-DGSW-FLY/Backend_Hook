package com.innosync.hook.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public abstract class WebSecurityConfigurerAdapter {

    public abstract void configure(HttpSecurity http) throws Exception;
}
