/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.exception.InvalidClientIdException;
import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.service.AuthorizationService;
import com.pamarin.oauth2.service.AuthorizationServiceImpl;
import com.pamarin.oauth2.service.ClientVerification;
import com.pamarin.oauth2.service.LoginSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AuthorizeEndpointCtrl.class)
public class AuthorizeEndpointCtrlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationService authorizationService;

    @Test
    public void shouldBeError400_whenEmptyParameter() throws Exception {
        this.mockMvc.perform(get("/api/v1/oauth/authorize"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBeError400_whenInvalidParameter1() throws Exception {
        this.mockMvc.perform(get("/api/v1/oauth/authorize?response_type=code"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBeError400_whenInvalidParameter2() throws Exception {
        this.mockMvc.perform(get("/api/v1/oauth/authorize?response_type=code&client_id=123456"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBeRedirect2Login_whenNotLogin() throws Exception {
        when(authorizationService.authorize(any(AuthorizationRequest.class))).thenReturn("response_type=code&client_id=123456&redirect_uri=http://localhost:8080/callback");
        this.mockMvc.perform(get("/api/v1/oauth/authorize?response_type=code&client_id=123456&redirect_uri=http://localhost:8080/callback"))
                .andExpect(status().isFound());
    }

    @Test
    public void shouldBeErrorInvalidClient_whenThrowInvalidClientIdException() throws Exception {
        when(authorizationService.authorize(any(AuthorizationRequest.class)))
                .thenThrow(InvalidClientIdException.class);
        this.mockMvc.perform(get("/api/v1/oauth/authorize?response_type=code&client_id=123456&redirect_uri=http://localhost:8080/callback"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost:8080/callback?error=invalid_client"));
    }
}
