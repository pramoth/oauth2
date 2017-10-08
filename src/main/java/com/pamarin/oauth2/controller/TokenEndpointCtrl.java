/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.model.AccessTokenResponse;
import com.pamarin.oauth2.model.CodeAccessTokenRequest;
import com.pamarin.oauth2.service.AccessTokenGenerator;
import com.pamarin.oauth2.util.HttpBasicAuthenParser;
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

    @Autowired
    private HttpBasicAuthenParser httpBasicAuthenParser;

    @Autowired
    private AccessTokenGenerator accessTokenGenerator;

    @ResponseBody
    @PostMapping(
            value = "/api/v1/oauth/token",
            consumes = "application/x-www-form-urlencoded",
            produces = "application/json"
    )
    public AccessTokenResponse getTokenByAuthorizationCode(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("grant_type") String grantType,
            @RequestParam("code") String code,
            @RequestParam("redirect_uri") String redirectUri
    ) {
        HttpBasicAuthenParser.Output basicAuthen = httpBasicAuthenParser.parse(authorization);
        return accessTokenGenerator.generate(new CodeAccessTokenRequest.Builder()
                .setClientId(basicAuthen.getUsername())
                .setClientSecret(basicAuthen.getPassword())
                .setCode(code)
                .setGrantType(grantType)
                .setRedirectUri(redirectUri)
                .build());
    }

}
