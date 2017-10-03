/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidResponseTypeException;
import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.model.AccessTokenResponse;
import com.pamarin.oauth2.model.AuthorizationResponse;
import com.pamarin.oauth2.validator.ResponseTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private ResponseTypeValidator responseTypeValidator;

    @Autowired
    private ClientVerification clientVerification;

    @Autowired
    private ScopeVerification scopeVerification;

    @Autowired
    private LoginSession loginSession;

    @Autowired
    private AuthorizationCodeGenerator authorizationCodeGenerator;

    @Autowired
    private AccessTokenGenerator accessTokenGenerator;

    private String getHostUrl() {
        return "";
    }

    @Override
    public String authorize(AuthorizationRequest authReq) {
        if (!responseTypeValidator.isValid(authReq.getResponseType())) {
            throw new InvalidResponseTypeException(authReq.getResponseType(), "Invalid responseType.");
        }

        clientVerification.verifyClientIdAndRedirectUri(authReq.getClientId(), authReq.getRedirectUri());
        scopeVerification.verifyByClientIdAndScope(authReq.getClientId(), authReq.getScope());
        if (loginSession.wasCreated()) {
            return obtainingAuthorization(authReq);
        } else {
            return getHostUrl() + "/login?" + authReq.buildQuerystring();
        }
    }

    private String obtainingAuthorization(AuthorizationRequest authReq) {
        if (authReq.responseTypeIsCode()) {
            return generateAuthorizationCode(authReq);
        }

        return generateAccessToken(authReq);
    }

    //https://tools.ietf.org/html/rfc6749#section-4.1.2
    private String generateAuthorizationCode(AuthorizationRequest authReq) {
        AuthorizationResponse response = authorizationCodeGenerator.generate(authReq);
        if (authReq.hasStateParam()) {
            response.setState(authReq.getState());
        }
        String uri = authReq.getRedirectUri();
        return uri + (uri.contains("?") ? "&" : "?") + response.buildQuerystring();
    }

    //https://tools.ietf.org/html/rfc6749#section-4.2.2
    private String generateAccessToken(AuthorizationRequest authReq) {
        AccessTokenResponse response = accessTokenGenerator.generate(authReq);
        if (authReq.hasStateParam()) {
            response.setState(authReq.getState());
        }
        return authReq.getRedirectUri() + "#" + response.buildQuerystringWithoutRefreshToken();
    }
}
