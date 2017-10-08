/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/08
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TokenEndpointCtrl.class)
public class TokenEndpointCtrlTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldBeErrorInvalidRequest_whenEmptyParameter() throws Exception {
        this.mockMvc.perform(post("/api/v1/oauth/token"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"error\":\"invalid_request\"}"));
    }

}
