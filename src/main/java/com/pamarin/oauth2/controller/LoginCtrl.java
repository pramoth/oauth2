/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.exception.InvalidUsernamePasswordException;
import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.model.LoginCredential;
import com.pamarin.oauth2.provider.HostUrlProvider;
import com.pamarin.oauth2.service.AuthorizationRequestVerification;
import com.pamarin.oauth2.service.UserVerification;
import com.pamarin.oauth2.view.ModelAndViewBuilder;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/24
 */
@Controller
public class LoginCtrl {

    private static final Logger LOG = LoggerFactory.getLogger(LoginCtrl.class);

    @Autowired
    private HostUrlProvider hostUrlProvider;

    @Autowired
    private AuthorizationRequestVerification requestVerification;

    @Autowired
    private UserVerification userVerification;

    private AuthorizationRequest buildAuthorizationRequest(HttpServletRequest httpReq) throws MissingServletRequestParameterException {
        AuthorizationRequest req = new AuthorizationRequest.Builder()
                .setResponseType(httpReq.getParameter("response_type"))
                .setClientId(httpReq.getParameter("client_id"))
                .setRedirectUri(httpReq.getParameter("redirect_uri"))
                .setScope(httpReq.getParameter("scope"))
                .setState(httpReq.getParameter("state"))
                .build();
        if (req.haveSomeParameters()) {
            req.validateParameters();
            requestVerification.verify(req);
        }
        return req;
    }

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest httpReq) throws MissingServletRequestParameterException {
        AuthorizationRequest req = buildAuthorizationRequest(httpReq);
        return new ModelAndViewBuilder()
                .setName("login")
                .addAttribute("error", httpReq.getParameter("error"))
                .addAttribute("processUrl", hostUrlProvider.provide() + "/login" + (req.haveSomeParameters() ? ("?" + req.buildQuerystring()) : ""))
                .build();
    }

    @PostMapping("/login")
    public void login(
            HttpServletRequest httpReq,
            HttpServletResponse httpResp,
            @Validated LoginCredential credential
    ) throws IOException, MissingServletRequestParameterException {
        AuthorizationRequest req = buildAuthorizationRequest(httpReq);
        try {
            userVerification.verifyUsernameAndPassword(
                    credential.getUsername(),
                    credential.getPassword()
            );
        } catch (InvalidUsernamePasswordException ex) {
            LOG.warn("Invalid username password ", ex);
            httpResp.sendRedirect(hostUrlProvider.provide() + "/login?error=invalid_username_password" + (req.haveSomeParameters() ? ("&" + req.buildQuerystring()) : ""));
        }
    }
}
