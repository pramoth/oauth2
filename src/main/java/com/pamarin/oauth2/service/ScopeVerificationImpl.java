/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidClientIdException;
import com.pamarin.oauth2.exception.InvalidScopeException;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/03
 */
@Service
public class ScopeVerificationImpl implements ScopeVerification {

    @Autowired
    private ScopeService scopeService;

    @Override
    public void verifyByClientIdAndScope(String clientId, String scope) {
        boolean isValid = hasText(clientId) && hasText(scope);
        if (!isValid) {
            throw new InvalidScopeException(scope, "Required clientId and scope.");
        }

        String[] arr = StringUtils.split(scope, ",");
        if (arr == null || arr.length < 1) {
            throw new InvalidScopeException(scope, "Invalid scope format.");
        }

        List<String> scopes = scopeService.findByClientId(clientId);
        if (isEmpty(scopes)) {
            throw new InvalidClientIdException(clientId, "Empty scopes.");
        }

        for (String s : arr) {
            if (!scopes.contains(s)) {
                throw new InvalidScopeException(s, "Invalid scope \"" + s + "\", it's not in [\"" + StringUtils.join(scopes, "\", \"") + "\"].");
            }
        }
    }

}
