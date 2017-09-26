/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.controller.LoginSession;
import com.pamarin.oauth2.model.AuthorizationRequest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
public class AuthorizationService_authorizeNotLoginTest {

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Mock
    private ClientVerification clientVerification;

    @Mock
    private LoginSession loginSession;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
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
