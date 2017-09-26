/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidClientIdAndRedirectUriException;
import com.pamarin.oauth2.repo.AllowDomainRepo;
import java.util.Arrays;
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
public class ClientVerification_verifyClientIdAndRedirectUriTest {

    @InjectMocks
    private ClientVerificationImpl clientVerification;

    @Mock
    private AllowDomainRepo allowDomainRepo;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
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
        when(allowDomainRepo.findDomainByClientId(any(String.class)))
                .thenReturn(Arrays.asList("https://pamarin.com"));

        String clientId = "123456";
        String redirectUri = "https://google.com";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

    @Test(expected = InvalidClientIdAndRedirectUriException.class)
    public void shouldBeThrowInvalidClientIdAndRedirectUriException_whenInvalidRedirectUriPath() {
        when(allowDomainRepo.findDomainByClientId(any(String.class)))
                .thenReturn(Arrays.asList("https://pamarin.com/callback"));

        String clientId = "123456";
        String redirectUri = "https://pamarin.com";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

    @Test
    public void shouldBeOk_whenValidRedirectUri() {
        when(allowDomainRepo.findDomainByClientId(any(String.class)))
                .thenReturn(Arrays.asList(
                        "https://pamarin.com",
                        "https://google.com"
                ));

        String clientId = "123456";
        String redirectUri = "https://google.com";
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
    }

}
