/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.provider.HostUrlProvider;
import com.pamarin.oauth2.service.AuthorizationRequestVerification;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private AuthorizationRequestVerification requestVerification;

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest httpReq) throws MissingServletRequestParameterException {
        AuthorizationRequest req = new AuthorizationRequest.Builder()
                .setResponseType(httpReq.getParameter("response_type"))
                .setClientId(httpReq.getParameter("client_id"))
                .setRedirectUri(httpReq.getParameter("redirect_uri"))
                .setScope(httpReq.getParameter("scope"))
                .setState(httpReq.getParameter("state"))
                .build();
        if (!req.haveSomeParameters()) {
            return new ModelAndView(
                    "login",
                    "processUrl",
                    hostUrlProvider.provide() + "/login");
        }
        req.validateParameters();
        requestVerification.verify(req);
        return new ModelAndView(
                "login",
                "processUrl",
                hostUrlProvider.provide() + "/login?" + req.buildQuerystring());
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
//        response.sendRedirect(hostUrlProvider.provide() + "/login?error=invalid_username_password" + makeAuthorizationRequest(
//                responseType,
//                clientId,
//                redirectUri,
//                scope,
//                state
//        ).buildQuerystring());
    }
}
