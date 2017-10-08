/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidResponseTypeException;
import com.pamarin.oauth2.model.AccessTokenResponse;
import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.model.AuthorizationResponse;
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
public class AuthorizationService_authorizeWasLoginTest {

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Mock
    private ResponseType.Validator responseTypeValidator;

    @Mock
    private ScopeVerification scopeVerification;

    @Mock
    private ClientVerification clientVerification;

    @Mock
    private LoginSession loginSession;

    @Mock
    private AuthorizationCodeGenerator authorizationCodeGenerator;

    @Mock
    private AccessTokenGenerator accessTokenGenerator;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = InvalidResponseTypeException.class)
    public void shouldBeThrowUnsupportedOperationException_whenInvalidResponseType() {
        when(loginSession.wasCreated()).thenReturn(true);
        when(responseTypeValidator.isValid(any(String.class))).thenReturn(false);

        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com/callback")
                .setResponseType("ABC")
                .setScope("read")
                .setState("XYZ")
                .build();

        String output = authorizationService.authorize(input);
    }

    @Test
    public void shouldBeReturnCode_whenResponseTypeIsCode() {
        when(loginSession.wasCreated()).thenReturn(true);
        when(responseTypeValidator.isValid(any(String.class))).thenReturn(true);
        when(authorizationCodeGenerator.generate(any(AuthorizationRequest.class)))
                .thenReturn(
                        new AuthorizationResponse.Builder()
                                .setCode("ABCD")
                                .build()
                );

        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com/callback")
                .setResponseType("code")
                .setScope("read")
                .build();

        String output = authorizationService.authorize(input);
        String expected = "https://pamarin.com/callback?code=ABCD";
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeReturnCode_whenResponseTypeIsCodeAndHasQuerystring() {
        when(loginSession.wasCreated()).thenReturn(true);
        when(responseTypeValidator.isValid(any(String.class))).thenReturn(true);
        when(authorizationCodeGenerator.generate(any(AuthorizationRequest.class)))
                .thenReturn(
                        new AuthorizationResponse.Builder()
                                .setCode("ABCD")
                                .build()
                );

        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com/callback?q=AAA")
                .setResponseType("code")
                .setScope("read")
                .build();

        String output = authorizationService.authorize(input);
        String expected = "https://pamarin.com/callback?q=AAA&code=ABCD";
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeReturnCodeAndState_whenResponseTypeIsCode() {
        when(loginSession.wasCreated()).thenReturn(true);
        when(responseTypeValidator.isValid(any(String.class))).thenReturn(true);
        when(authorizationCodeGenerator.generate(any(AuthorizationRequest.class)))
                .thenReturn(
                        new AuthorizationResponse.Builder()
                                .setCode("ABCD")
                                .build()
                );

        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com/callback")
                .setResponseType("code")
                .setScope("read")
                .setState("XYZ")
                .build();

        String output = authorizationService.authorize(input);
        String expected = "https://pamarin.com/callback?code=ABCD&state=XYZ";
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeReturnToken_whenResponseTypeIsToken() {
        when(loginSession.wasCreated()).thenReturn(true);
        when(responseTypeValidator.isValid(any(String.class))).thenReturn(true);
        when(accessTokenGenerator.generate(any(AuthorizationRequest.class)))
                .thenReturn(
                        new AccessTokenResponse.Builder()
                                .setAccessToken("ABCDEF")
                                .setTokenType("bearer")
                                .setExpiresIn(3600)
                                .build()
                );

        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com/callback")
                .setResponseType("token")
                .setScope("read")
                .setState("XYZ")
                .build();

        String output = authorizationService.authorize(input);
        String expected = "https://pamarin.com/callback#access_token=ABCDEF&state=XYZ&token_type=bearer&expires_in=3600";
        assertThat(output).isEqualTo(expected);
    }
}
