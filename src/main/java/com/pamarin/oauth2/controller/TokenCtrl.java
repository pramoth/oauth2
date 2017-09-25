/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.model.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TokenCtrl {
    
    private static final Logger LOG = LoggerFactory.getLogger(TokenCtrl.class);

    @ResponseBody
    @PostMapping(
            value = "/api/v1/oauth/token", 
            params = "grant_type=authorization_code",
            consumes = "application/x-www-form-urlencoded"
    )
    public AccessTokenResponse getTokenByCode(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("grant_type") String grantType,
            @RequestParam("code") String code,
            @RequestParam("redirect_uri") String redirectUri
    ) {
        
        LOG.debug("authorization => {}", authorization);
        
        return new AccessTokenResponse();
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
        
        LOG.debug("authorization => {}", authorization);
        
        return new AccessTokenResponse();
    }

}
