/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/11
 */
public class AuthorizationRequest_validateParametersTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldBeOk_whenEmptyParameters() throws MissingServletRequestParameterException {
        AuthorizationRequest request = new AuthorizationRequest();
        request.validateParameters();
    }

    @Test
    public void shouldBeRequireResponseType_whenHaveSomeValue() throws MissingServletRequestParameterException {

        exception.expect(MissingServletRequestParameterException.class);
        exception.expectMessage("Required String parameter 'response_type' is not present");

        AuthorizationRequest request = new AuthorizationRequest.Builder()
                .setClientId("123456")
                .build();
        request.validateParameters();
    }

    @Test
    public void shouldBeRequireClientId_whenHaveSomeValue() throws MissingServletRequestParameterException {

        exception.expect(MissingServletRequestParameterException.class);
        exception.expectMessage("Required String parameter 'client_id' is not present");

        AuthorizationRequest request = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .build();
        request.validateParameters();
    }

    @Test
    public void shouldBeRequireRedirectUri_whenHaveSomeValue() throws MissingServletRequestParameterException {

        exception.expect(MissingServletRequestParameterException.class);
        exception.expectMessage("Required String parameter 'redirect_uri' is not present");

        AuthorizationRequest request = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .setClientId("123456")
                .build();
        request.validateParameters();
    }

    @Test
    public void shouldBeOk_whenHaveAllRequireValues() throws MissingServletRequestParameterException {
        AuthorizationRequest request = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .setClientId("123456")
                .setRedirectUri("http://localhost/callback")
                .setScope("read")
                .build();
        request.validateParameters();
    }

}
