/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.RequireApprovalException;
import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.model.AccessTokenResponse;
import com.pamarin.oauth2.model.AuthorizationResponse;
import com.pamarin.oauth2.provider.HostUrlProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private HostUrlProvider hostUrlProvider;

    @Autowired
    private LoginSession loginSession;

    @Autowired
    private AuthorizationCodeGenerator authorizationCodeGenerator;

    @Autowired
    private AccessTokenGenerator accessTokenGenerator;

    @Autowired
    private ApprovalService approvalService;
    
    @Autowired
    private AuthorizationRequestVerification requestVerification;

    @Override
    public String authorize(AuthorizationRequest req) {
        requestVerification.verify(req);
        if (loginSession.wasCreated()) {
            if (!approvalService.wasApprovedByUserIdAndClientId(loginSession.getUserId(), req.getClientId())) {
                throw new RequireApprovalException();
            }
            return obtainingAuthorization(req);
        } else {
            return hostUrlProvider.provide() + "/login?" + req.buildQuerystring();
        }
    }

    private String obtainingAuthorization(AuthorizationRequest req) {
        if (req.responseTypeIsCode()) {
            return generateAuthorizationCode(req);
        }

        return generateAccessToken(req);
    }

    //https://tools.ietf.org/html/rfc6749#section-4.1.2
    private String generateAuthorizationCode(AuthorizationRequest req) {
        AuthorizationResponse response = authorizationCodeGenerator.generate(req);
        if (req.hasStateParam()) {
            response.setState(req.getState());
        }
        String uri = req.getRedirectUri();
        return uri + (uri.contains("?") ? "&" : "?") + response.buildQuerystring();
    }

    //https://tools.ietf.org/html/rfc6749#section-4.2.2
    private String generateAccessToken(AuthorizationRequest req) {
        AccessTokenResponse response = accessTokenGenerator.generate(req);
        if (req.hasStateParam()) {
            response.setState(req.getState());
        }
        return req.getRedirectUri() + "#" + response.buildQuerystringWithoutRefreshToken();
    }
}
