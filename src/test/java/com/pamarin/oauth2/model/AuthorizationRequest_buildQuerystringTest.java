/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
public class AuthorizationRequest_buildQuerystringTest {

    @Test
    public void shouldBeOk_whenValidRequest1() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com")
                .setScope("read")
                .build();
        String output = input.buildQuerystring();
        String expected = "response_type=code&client_id=1234&redirect_uri=https%3A%2F%2Fpamarin.com&scope=read";
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeOk_whenValidRequest2() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("token")
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com")
                .setState("XYZ")
                .setScope("read")
                .build();
        String output = input.buildQuerystring();
        String expected = "response_type=token&client_id=1234&redirect_uri=https%3A%2F%2Fpamarin.com&scope=read&state=XYZ";
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeOk_whenValidRequest3() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com")
                .setState("XYZ")
                .setScope("read")
                .build();
        String output = input.buildQuerystring();
        String expected = "response_type=code&client_id=1234&redirect_uri=https%3A%2F%2Fpamarin.com&scope=read&state=XYZ";
        assertThat(output).isEqualTo(expected);
    }

}
