/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/21
 */
public class LoginCredential {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
