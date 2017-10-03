/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidScopeException;
import org.springframework.stereotype.Service;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/03
 */
@Service
public class ScopeVerificationImpl implements ScopeVerification {

    @Override
    public void verifyByClientIdAndScope(String clientId, String scope) {
        boolean isValid = hasText(clientId) && hasText(scope);
        if(!isValid){
            throw new InvalidScopeException(scope, "Required clientId and scope.");
        }
    }

}
