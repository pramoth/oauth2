/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidClientIdAndRedirectUriException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
public class ClientVerificationTest {

    private ClientVerificationImpl clientVerification;

    @Before
    public void before() {
        clientVerification = new ClientVerificationImpl();
    }

    @Test(expected = InvalidClientIdAndRedirectUriException.class)
    public void shouldBeThrowInvalidClientIdAndRedirectUriException_whenClientIdAndRedirectUriIsNull() {
        String clientId = null;
        String redirectUri = null;
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

    @Test(expected = InvalidClientIdAndRedirectUriException.class)
    public void shouldBeThrowInvalidClientIdAndRedirectUriException_whenClientIdAndRedirectUriIsEmptyString() {
        String clientId = "";
        String redirectUri = "";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

    @Test(expected = InvalidClientIdAndRedirectUriException.class)
    public void shouldBeThrowInvalidClientIdAndRedirectUriException_whenInvalidRedirectUri() {
        clientVerification.getAllowDomains().add("https://pamarin.com");

        String clientId = "123456";
        String redirectUri = "https://google.com";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

    @Test(expected = InvalidClientIdAndRedirectUriException.class)
    public void shouldBeThrowInvalidClientIdAndRedirectUriException_whenInvalidRedirectUriPath() {
        clientVerification.getAllowDomains().add("https://pamarin.com/callback");

        String clientId = "123456";
        String redirectUri = "https://pamarin.com";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

    @Test
    public void shouldBeOk_whenValidRedirectUri() {
        clientVerification.getAllowDomains().add("https://pamarin.com");
        clientVerification.getAllowDomains().add("https://google.com");

        String clientId = "123456";
        String redirectUri = "https://google.com";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

}
