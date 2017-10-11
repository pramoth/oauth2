/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.DefaultAuthorizationRequest;
import com.pamarin.oauth2.exception.InvalidResponseTypeException;
import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.provider.HostUrlProvider;
import com.pamarin.oauth2.service.ClientVerification;
import com.pamarin.oauth2.validator.ResponseType;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import static org.springframework.util.StringUtils.hasText;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/24
 */
@Controller
public class LoginCtrl {

    @Autowired
    private HostUrlProvider hostUrlProvider;

    @Autowired
    private DefaultAuthorizationRequest defaultAuthorizationRequest;

    @Autowired
    private ResponseType.Validator responseTypeValidator;

    @Autowired
    private ClientVerification clientVerification;

    private AuthorizationRequest makeAuthorizationRequest(
            String responseType,
            String clientId,
            String redirectUri,
            String scope,
            String state
    ) {
        AuthorizationRequest authReq = new AuthorizationRequest.Builder()
                .setClientId(clientId)
                .setRedirectUri(redirectUri)
                .setResponseType(responseType)
                .setScope(scope)
                .setState(state)
                .build();
        if (!authReq.isValidRequest()) {
            authReq = defaultAuthorizationRequest.getDefault();
        }
        return authReq;
    }

    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "response_type", required = false) String responseType,
            @RequestParam(value = "client_id", required = false) String clientId,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "state", required = false) String state
    ) throws MissingServletRequestParameterException {
        AuthorizationRequest authReq = new AuthorizationRequest.Builder()
                .setClientId(clientId)
                .setRedirectUri(redirectUri)
                .setResponseType(responseType)
                .setScope(scope)
                .setState(state)
                .build();
        authReq.validateParameters();
        return new ModelAndView(
                "login",
                "processUrl",
                hostUrlProvider.provide() + "/login");
    }

    @PostMapping("/login")
    public void postLogin(
            @RequestParam(value = "response_type", required = false) String responseType,
            @RequestParam(value = "client_id", required = false) String clientId,
            @RequestParam(value = "redirect_uri", required = false) String redirectUri,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response
    ) throws IOException {
        response.sendRedirect(hostUrlProvider.provide() + "/login?error=invalid_username_password" + makeAuthorizationRequest(
                responseType,
                clientId,
                redirectUri,
                scope,
                state
        ).buildQuerystring());
    }
}
