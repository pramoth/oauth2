/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.controller.LoginSession;
import com.pamarin.oauth2.model.AccessTokenResponse;
import com.pamarin.oauth2.model.AuthorizationRequest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
public class AuthorizationService_authorizeWasLoginTest {

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Mock
    private ClientVerification clientVerification;

    @Mock
    private LoginSession loginSession;

    @Mock
    private AccessTokenGenerator accessTokenGenerator;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldBeThrowUnsupportedOperationException_whenInvalidResponseType() {
        when(loginSession.wasCreated()).thenReturn(true);

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

        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com/callback")
                .setResponseType("code")
                .setScope("read")
                .build();

        String output = authorizationService.authorize(input);
        String expected = "https://pamarin.com/callback?code=null";
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeReturnCode_whenResponseTypeIsCodeAndHasQuerystring() {
        when(loginSession.wasCreated()).thenReturn(true);

        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com/callback?q=AAA")
                .setResponseType("code")
                .setScope("read")
                .build();

        String output = authorizationService.authorize(input);
        String expected = "https://pamarin.com/callback?q=AAA&code=null";
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeReturnCodeAndState_whenResponseTypeIsCode() {
        when(loginSession.wasCreated()).thenReturn(true);

        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setClientId("1234")
                .setRedirectUri("https://pamarin.com/callback")
                .setResponseType("code")
                .setScope("read")
                .setState("XYZ")
                .build();

        String output = authorizationService.authorize(input);
        String expected = "https://pamarin.com/callback?code=null&state=XYZ";
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeReturnToken_whenResponseTypeIsToken() {
        when(loginSession.wasCreated()).thenReturn(true);
        when(accessTokenGenerator.generate(any(AuthorizationRequest.class)))
                .thenReturn(
                        new AccessTokenResponse.Builder()
                                .setAccessToken("ABCDEF")
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
        String expected = "https://pamarin.com/callback#token=ABCDEF";
        assertThat(output).isEqualTo(expected);
    }
}
