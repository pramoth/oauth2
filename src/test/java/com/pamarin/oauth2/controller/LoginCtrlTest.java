/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.DefaultAuthorizationRequest;
import com.pamarin.oauth2.exception.InvalidClientIdAndRedirectUriException;
import com.pamarin.oauth2.exception.InvalidRedirectUriException;
import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.provider.HostUrlProvider;
import com.pamarin.oauth2.service.ClientVerification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/11
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LoginCtrl.class)
public class LoginCtrlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HostUrlProvider hostUrlProvider;

    @MockBean
    private DefaultAuthorizationRequest defaultAuthorizationRequest;

    @MockBean
    private ClientVerification clientVerification;

    @Before
    public void before() {
        when(hostUrlProvider.provide()).thenReturn("http://localhost");
        when(defaultAuthorizationRequest.getDefault())
                .thenReturn(defaultAuthorizationRequest());
    }

    private AuthorizationRequest defaultAuthorizationRequest() {
        return new AuthorizationRequest.Builder()
                .setClientId("123456")
                .setRedirectUri("http://localhost")
                .setResponseType("code")
                .setScope("read")
                .setState("XYZ")
                .build();
    }

    @Test
    public void shouldBeGetLoginViewAndDefaultAuthorizationRequest_whenEmptyQuerystringParameters() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("processUrl", "http://localhost/login"));
    }

    @Test
    public void shouldBeErrorInvalidRequest_whenResponseTypeIsAAA() throws Exception {
        this.mockMvc.perform(get("/login?response_type=AAA"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("invalid_request"));
    }

    @Test
    public void shouldBeErrorInvalidRequest_whenClientIdIs000000AndRedirectUriIsEmpty() throws Exception {
        this.mockMvc.perform(get("/login?response_type=code&client_id=000000"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("invalid_request"));
    }

    @Test
    public void shouldBeErrorInvalidRequest_whenEmptyClientIdAndRedirectUriIsAAA() throws Exception {
        this.mockMvc.perform(get("/login?response_type=code&redirect_uri=AAA"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("invalid_request"));
    }

    @Test
    public void shouldBeErrorInvalidRequest_whenClientIdIs000000AndInvalidRedirectUri() throws Exception {
        doThrow(InvalidRedirectUriException.class)
                .when(clientVerification)
                .verifyClientIdAndRedirectUri("000000", "AAA");
        this.mockMvc.perform(get("/login?response_type=code&client_id=000000&redirect_uri=AAA"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("invalid_request"));
    }
}
