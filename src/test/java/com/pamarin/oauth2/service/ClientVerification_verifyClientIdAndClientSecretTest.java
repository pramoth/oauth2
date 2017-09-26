/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidClientIdAndClientSecretException;
import com.pamarin.oauth2.repo.ClientRepo;
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
public class ClientVerification_verifyClientIdAndClientSecretTest {

    @InjectMocks
    private ClientVerificationImpl clientVerification;

    @Mock
    private ClientRepo clientRepo;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = InvalidClientIdAndClientSecretException.class)
    public void shouldBeThrowInvalidClientIdAndClientSecretException_whenClientIdAndClientSecretIsNull() {
        String clientId = null;
        String clientSecret = null;
        clientVerification.verifyClientIdAndClientSecret(clientId, clientSecret);
    }

    @Test(expected = InvalidClientIdAndClientSecretException.class)
    public void shouldBeThrowInvalidClientIdAndClientSecretException_whenClientIdAndClientSecretIsEmptyString() {
        String clientId = "";
        String clientSecret = "";
        clientVerification.verifyClientIdAndClientSecret(clientId, clientSecret);
    }

    @Test(expected = InvalidClientIdAndClientSecretException.class)
    public void shouldBeThrowInvalidClientIdAndClientSecretException_whenNotFoundClientSecret() {
        when(clientRepo.findClientSecretByClientId(any(String.class)))
                .thenReturn(null);

        String clientId = "123456";
        String clientSecret = "xyz";
        clientVerification.verifyClientIdAndClientSecret(clientId, clientSecret);
    }

    @Test(expected = InvalidClientIdAndClientSecretException.class)
    public void shouldBeThrowInvalidClientIdAndClientSecretException_whenInvalidClientSecret() {
        when(clientRepo.findClientSecretByClientId(any(String.class)))
                .thenReturn("password");

        String clientId = "123456";
        String clientSecret = "xyz";
        clientVerification.verifyClientIdAndClientSecret(clientId, clientSecret);
    }

    @Test
    public void shouldBeOk_whenValidClientSecret() {
        when(clientRepo.findClientSecretByClientId(any(String.class)))
                .thenReturn("password");

        String clientId = "123456";
        String clientSecret = "password";
        clientVerification.verifyClientIdAndClientSecret(clientId, clientSecret);
    }
}
