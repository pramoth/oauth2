/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidClientIdAndRedirectUriException;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
public class ClientVerification_verifyClientIdAndRedirectUriTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private ClientVerificationImpl clientVerification;

    @Mock
    private AllowDomainService allowDomainService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldBeThrowInvalidClientIdAndRedirectUriException_whenClientIdAndRedirectUriIsNull() {

        exception.expect(InvalidClientIdAndRedirectUriException.class);
        exception.expectMessage("Required clientId and redirectUri.");

        String clientId = null;
        String redirectUri = null;
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

    @Test
    public void shouldBeThrowInvalidClientIdAndRedirectUriException_whenClientIdAndRedirectUriIsEmptyString() {

        exception.expect(InvalidClientIdAndRedirectUriException.class);
        exception.expectMessage("Required clientId and redirectUri.");

        String clientId = "";
        String redirectUri = "";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

    @Test
    public void shouldBeThrowInvalidClientIdAndRedirectUriException_whenInvalidRedirectUri() {

        exception.expect(InvalidClientIdAndRedirectUriException.class);
        exception.expectMessage("Invalid Domains.");

        when(allowDomainService.findDomainByClientId(any(String.class)))
                .thenReturn(Arrays.asList("https://pamarin.com"));

        String clientId = "123456";
        String redirectUri = "https://google.com";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

    @Test
    public void shouldBeThrowInvalidClientIdAndRedirectUriException_whenInvalidRedirectUriPath() {

        exception.expect(InvalidClientIdAndRedirectUriException.class);
        exception.expectMessage("Invalid Domains.");

        when(allowDomainService.findDomainByClientId(any(String.class)))
                .thenReturn(Arrays.asList("https://pamarin.com/callback"));

        String clientId = "123456";
        String redirectUri = "https://pamarin.com";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

    @Test
    public void shouldBeOk_whenValidRedirectUri() {
        
        when(allowDomainService.findDomainByClientId(any(String.class)))
                .thenReturn(Arrays.asList(
                        "https://pamarin.com",
                        "https://google.com"
                ));

        String clientId = "123456";
        String redirectUri = "https://google.com";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

}
