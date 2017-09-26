/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.model.AccessTokenResponse;
import com.pamarin.oauth2.model.CodeAccessTokenRequest;
import com.pamarin.oauth2.model.RefreshAccessTokenRequest;
import com.pamarin.oauth2.service.AccessTokenGenerator;
import com.pamarin.oauth2.service.ClientVerification;
import com.pamarin.oauth2.util.HttpBasicAuthenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
@Controller
public class TokenEndpointCtrl {

    private static final Logger LOG = LoggerFactory.getLogger(TokenEndpointCtrl.class);

    @Autowired
    private HttpBasicAuthenParser httpBasicAuthenParser;

    @Autowired
    private AccessTokenGenerator accessTokenGenerator;
    
    @Autowired
    private ClientVerification clientVerification;

    @ResponseBody
    @PostMapping(
            value = "/api/v1/oauth/token",
            params = "grant_type=authorization_code",
            consumes = "application/x-www-form-urlencoded"
    )
    public AccessTokenResponse getTokenByAuthorizationCode(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("grant_type") String grantType,
            @RequestParam("code") String code,
            @RequestParam("redirect_uri") String redirectUri
    ) {
        HttpBasicAuthenParser.Output basicAuthen = httpBasicAuthenParser.parse(authorization);
        clientVerification.verifyClientIdAndClientSecret(basicAuthen.getUsername(), basicAuthen.getPassword());
        return accessTokenGenerator.generate(new CodeAccessTokenRequest.Builder()
                .setClientId(basicAuthen.getUsername())
                .setClientSecret(basicAuthen.getPassword())
                .setCode(code)
                .setGrantType(grantType)
                .setRedirectUri(redirectUri)
                .build());
    }

    @ResponseBody
    @PostMapping(
            value = "/api/v1/oauth/token",
            params = "grant_type=refresh_token",
            consumes = "application/x-www-form-urlencoded"
    )
    public AccessTokenResponse getTokenByRefreshToken(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("grant_type") String grantType,
            @RequestParam("refresh_token") String refreshToken,
            @RequestParam("redirect_uri") String redirectUri
    ) {
        HttpBasicAuthenParser.Output basicAuthen = httpBasicAuthenParser.parse(authorization);
        clientVerification.verifyClientIdAndClientSecret(basicAuthen.getUsername(), basicAuthen.getPassword());
        return accessTokenGenerator.generate(new RefreshAccessTokenRequest.Builder()
                .setClientId(basicAuthen.getUsername())
                .setClientSecret(basicAuthen.getPassword())
                .setRefreshToken(refreshToken)
                .setGrantType(grantType)
                .setRedirectUri(redirectUri)
                .build());
    }

}
