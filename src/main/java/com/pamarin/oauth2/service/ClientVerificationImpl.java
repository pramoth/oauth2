/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidClientIdAndClientSecretException;
import com.pamarin.oauth2.exception.InvalidClientIdAndRedirectUriException;
import com.pamarin.oauth2.repo.AllowDomainRepo;
import com.pamarin.oauth2.repo.ClientRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
@Service
public class ClientVerificationImpl implements ClientVerification {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private AllowDomainRepo allowDomainRepo;

    @Override
    public void verifyClientIdAndRedirectUri(String clientId, String redirectUri) {
        boolean isValid = hasText(clientId) && hasText(redirectUri);
        if (!isValid) {
            throw new InvalidClientIdAndRedirectUriException(clientId, redirectUri);
        }

        List<String> domains = allowDomainRepo.findDomainByClientId(clientId);
        for (String domain : domains) {
            if (redirectUri.startsWith(domain)) {
                return;
            }
        }

        throw new InvalidClientIdAndRedirectUriException(clientId, redirectUri);
    }

    @Override
    public void verifyClientIdAndClientSecret(String clientId, String clientSecret) {
        boolean isValid = hasText(clientId) && hasText(clientSecret);
        if (!isValid) {
            throw new InvalidClientIdAndClientSecretException(clientId, clientSecret);
        }

        String secret = clientRepo.findClientSecretByClientId(clientId);
        if (!hasText(secret)) {
            throw new InvalidClientIdAndClientSecretException(clientId, clientSecret);
        }

        if (!secret.equals(clientSecret)) {
            throw new InvalidClientIdAndClientSecretException(clientId, clientSecret);
        }
    }

}
