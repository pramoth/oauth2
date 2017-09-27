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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    
    @Test
    public void shouldBeError() throws Exception{
        this.mockMvc.perform(get("/api/v1/oauth/authorize"))
                .andExpect(status().isBadRequest());
    }

}
