/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import org.springframework.stereotype.Component;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
@Component
public class LoginSessionImpl implements LoginSession {

    @Override
    public boolean wasCreated() {
        return true;
    }

}
