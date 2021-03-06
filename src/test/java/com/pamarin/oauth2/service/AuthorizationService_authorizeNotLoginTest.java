/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.provider.HostUrlProvider;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import com.pamarin.oauth2.validator.ResponseType;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
public class AuthorizationService_authorizeNotLoginTest {

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Mock
    private LoginSession loginSession;

    @Mock
    private HostUrlProvider hostUrlProvider;

    @Mock
    private AuthorizationRequestVerification requestVerification;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(hostUrlProvider.provide()).thenReturn("");
    }

    @Test
    public void shouldBeReturnLoginUri_whenNotLogin() {
        when(loginSession.wasCreated()).thenReturn(false);

        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com/callback")
                .setResponseType("code")
                .setScope("read")
                .setState("XYZ")
                .build();

        String output = authorizationService.authorize(input);
        String expected = "/login?" + input.buildQuerystring();
        assertThat(output).isEqualTo(expected);
    }
}
