/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import com.pamarin.oauth2.exception.InvalidUsernamePasswordException;
import com.pamarin.oauth2.provider.HostUrlProvider;
import com.pamarin.oauth2.service.UserVerification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/21
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LoginCtrl.class)
public class LoginCtrl_postLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HostUrlProvider hostUrlProvider;

    @MockBean
    private UserVerification userVerification;

    @Before
    public void before() {
        when(hostUrlProvider.provide()).thenReturn("http://localhost");
    }

    @Test
    public void shouldBeInvalidRequest_whenUsernameAndPasswordIsEmpty() throws Exception {
        this.mockMvc.perform(post("/login?response_type=code&client_id=000000&redirect_uri=http://localhost/callback&scope=read"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/callback?error=invalid_request"));
    }

    @Test
    public void shouldBeInvalidUsernamePassword_whenUsernameAndPasswordIsAAA() throws Exception {
        doThrow(InvalidUsernamePasswordException.class)
                .when(userVerification)
                .verifyUsernameAndPassword("AAA", "AAA");
        this.mockMvc.perform(
                post("/login?response_type=code&client_id=000000&redirect_uri=http://localhost/callback&scope=read")
                        .param("username", "AAA")
                        .param("password", "AAA")
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login?error=invalid_username_password&response_type=code&client_id=000000&redirect_uri=http%3A%2F%2Flocalhost%2Fcallback&scope=read"));
    }

    @Test
    public void shouldBeOk() throws Exception {
        this.mockMvc.perform(
                post("/login?response_type=code&client_id=000000&redirect_uri=http://localhost/callback&scope=read")
                        .param("username", "test")
                        .param("password", "password")
        )
                .andExpect(status().isOk());
    }
}
