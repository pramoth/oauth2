/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.exception.RequireApprovalException;
import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.service.AuthorizationService;
import com.pamarin.oauth2.view.ModelAndViewBuilder;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/24
 */
@Controller
@RequestMapping("/oauth/v1/authorize")
public class AuthorizeEndpointCtrl {

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    public ModelAndView authorize(
            @RequestParam("response_type") String responseType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(name = "state", required = false) String state,
            HttpServletResponse httpResp
    ) throws IOException {
        AuthorizationRequest req = new AuthorizationRequest.Builder()
                .setClientId(clientId)
                .setRedirectUri(redirectUri)
                .setResponseType(responseType)
                .setScope(scope)
                .setState(state)
                .build();
        try {
            httpResp.sendRedirect(authorizationService.authorize(req));
            return null;
        } catch (RequireApprovalException ex) {
            return new ModelAndViewBuilder()
                    .setName("authorize")
                    .addAttribute("processUrl", "/oauth/v1/authorize?" + req.buildQuerystring())
                    .build();
        }
    }

    @PostMapping(params = "answer=allow")
    public void allow(
            @RequestParam("response_type") String responseType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(name = "state", required = false) String state,
            HttpServletResponse httpResp
    ) throws IOException {
        httpResp.sendRedirect(authorizationService.allow(new AuthorizationRequest.Builder()
                .setClientId(clientId)
                .setRedirectUri(redirectUri)
                .setResponseType(responseType)
                .setScope(scope)
                .setState(state)
                .build()));
    }
}
