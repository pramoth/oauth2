/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.exception.InvalidClientIdException;
import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.model.ErrorResponse;
import com.pamarin.oauth2.service.AuthorizationService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/24
 */
@Controller
public class AuthorizeEndpointCtrl {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizeEndpointCtrl.class);

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping(value = "/api/v1/oauth/authorize", params = "response_type=code")
    public void authorizeReturnCode(
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

    @GetMapping(value = "/api/v1/oauth/authorize", params = "response_type=token")
    public void authorizeReturnToken(
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public void unsupportedResponseType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse.unsupportedResponseType()
                .returnError(request, response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidClientIdException.class)
    public void invalidClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse.invalidClient()
                .returnError(request, response);
    }
}
