/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.service.AuthorizationService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/24
 */
@Controller
public class AuthorizeCtrl {

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping(value = "/api/v1/oauth/authorize", params = {
        "response_type=code",
        "response_type=token"
    })
    public void authorize(
            @RequestParam("response_type") String responseType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam(value = "scope", required = false, defaultValue = "read") String scope,
            @RequestParam(name = "state", required = false) String state,
            HttpServletResponse resp
    ) throws IOException {
        resp.sendRedirect(authorizationService.authorize(new AuthorizationRequest.Builder()
                .setClientId(clientId)
                .setRedirectUri(redirectUri)
                .setResponseType(responseType)
                .setScope(scope)
                .setState(state)
                .build()
        ));
    }
}
