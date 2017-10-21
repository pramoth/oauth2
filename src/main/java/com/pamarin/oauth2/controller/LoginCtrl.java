/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.provider.HostUrlProvider;
import com.pamarin.oauth2.service.AuthorizationRequestVerification;
import com.pamarin.oauth2.view.ModelAndViewBuilder;
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
        String queryString = "";
        if (req.haveSomeParameters()) {
            req.validateParameters();
            requestVerification.verify(req);
            queryString = "?" + req.buildQuerystring();
        }
        return new ModelAndViewBuilder()
                .setName("login")
                .addAttribute("error", httpReq.getParameter("error"))
                .addAttribute("processUrl", hostUrlProvider.provide() + "/login" + queryString)
                .build();
    }

    @PostMapping("/login")
    public void postLogin(HttpServletRequest httpReq,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response
    ) throws IOException {
        AuthorizationRequest req = new AuthorizationRequest.Builder()
                .setResponseType(httpReq.getParameter("response_type"))
                .setClientId(httpReq.getParameter("client_id"))
                .setRedirectUri(httpReq.getParameter("redirect_uri"))
                .setScope(httpReq.getParameter("scope"))
                .setState(httpReq.getParameter("state"))
                .build();
        response.sendRedirect(hostUrlProvider.provide() + "/login?error=invalid_username_password&" + req.buildQuerystring());
    }
}
