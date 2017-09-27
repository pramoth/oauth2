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
public class AuthorizationRequest_isValidRequestTest {

    @Test
    public void shouldBeFalse_whenRequestValueIsAllNull() {
        AuthorizationRequest input = new AuthorizationRequest();
        boolean output = input.isValidRequest();
        boolean expected = false;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeFalse_whenResponseTypeIsCode() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .build();
        boolean output = input.isValidRequest();
        boolean expected = false;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeFalse_whenResponseTypeIsCodeAndClientIdIs1234() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .setClientId("1234")
                .build();
        boolean output = input.isValidRequest();
        boolean expected = false;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeTrue_whenRequestIsValid() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com")
                .build();
        boolean output = input.isValidRequest();
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }

}
