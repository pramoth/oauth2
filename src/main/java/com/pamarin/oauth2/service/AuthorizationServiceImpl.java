/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.controller.LoginSession;
import com.pamarin.oauth2.model.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private ClientVerification clientVerification;

    @Autowired
    private LoginSession loginSession;

    @Autowired
    private AccessTokenGenerator accessTokenGenerator;

    private String getHostUrl() {
        return "";
    }

    private String generateCode(AuthorizationRequest authReq) {
        return null;
    }

    @Override
    public String authorize(AuthorizationRequest authReq) {
        clientVerification.verifyClientIdAndRedirectUri(authReq.getClientId(), authReq.getRedirectUri());
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

        if (authReq.responseTypeIsToken()) {
            AccessTokenResponse response = accessTokenGenerator.generate(authReq);
            response.setState(authReq.getState());
            return authReq.getRedirectUri() + "#" + response.buildQuerystringWithoutRefreshToken();
        }

        throw new UnsupportedOperationException("Unsuported response_type=" + authReq.getResponseType());
    }

    private String generateAuthorizationCode(AuthorizationRequest authReq) {
        String code = generateCode(authReq);
        String uri = authReq.getRedirectUri();
        StringBuilder builder = new StringBuilder()
                .append(uri)
                .append(uri.contains("?") ? "&" : "?")
                .append("code=")
                .append(code);

        if (authReq.hasStateParam()) {
            builder.append("&state=")
                    .append(authReq.getState());
        }

        return builder.toString();
    }

}
