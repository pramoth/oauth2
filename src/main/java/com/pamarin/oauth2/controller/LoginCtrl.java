/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/24
 */
@Controller
public class LoginCtrl {

    private String getHostUrl() {
        return "";
    }
    
    private String makeLoginSuccessUri(AuthorizaionRequest authReq){
        return getHostUrl() + "/api/v1/oauth/authorize?" + authReq.toString();
    }

    @GetMapping("/login")
    public void login(
            @RequestParam(value = "response_type", required = false) String responseType,
            @RequestParam(value = "client_id", required = false) String clientId,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "state", required = false) String state
    ) {

        AuthorizaionRequest authReq = new AuthorizaionRequest.Builder()
                .setClientId(clientId)
                .setRedirectUri(redirectUri)
                .setResponseType(responseType)
                .setScope(scope)
                .setState(state)
                .build();

        if (!authReq.isValidRequest()) {
            authReq = createDefaultAuthorizaionRequest();
        }

        String loginSccessUri = makeLoginSuccessUri(authReq);
    }

    private AuthorizaionRequest createDefaultAuthorizaionRequest() {
        return new AuthorizaionRequest();
    }
}
