/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/22
 */
public interface PasswordEncryption {

    boolean matches(String rawPassword, String encryptedPassword);
    
}
