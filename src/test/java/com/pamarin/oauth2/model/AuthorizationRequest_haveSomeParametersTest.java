/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/11
 */
public class AuthorizationRequest_haveSomeParametersTest {

    @Test
    public void shouldBeFalse_whenNullAllParameters() {
        AuthorizationRequest input = new AuthorizationRequest();
        boolean output = input.haveSomeParameters();
        boolean expected = false;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeTrue_whenHaveSomeParameters1() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setClientId("123456")
                .build();
        boolean output = input.haveSomeParameters();
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeTrue_whenHaveSomeParameters2() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setRedirectUri("http://localhost")
                .build();
        boolean output = input.haveSomeParameters();
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeTrue_whenHaveSomeParameters3() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .build();
        boolean output = input.haveSomeParameters();
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeTrue_whenHaveSomeParameters4() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setScope("read")
                .build();
        boolean output = input.haveSomeParameters();
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeTrue_whenHaveSomeParameters5() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setState("XYZ")
                .build();
        boolean output = input.haveSomeParameters();
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }
}
