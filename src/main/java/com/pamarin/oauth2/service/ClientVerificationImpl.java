/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidClientIdAndClientSecretException;
import com.pamarin.oauth2.exception.InvalidClientIdAndRedirectUriException;
import com.pamarin.oauth2.exception.InvalidClientIdException;
import com.pamarin.oauth2.exception.InvalidRedirectUriException;
import com.pamarin.oauth2.validator.ValidUri;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.CollectionUtils.isEmpty;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
@Service
public class ClientVerificationImpl implements ClientVerification {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AllowDomainService allowDomainService;

    @Autowired
    private ValidUri.Validator validUriValidator;

    @Override
    public void verifyClientIdAndRedirectUri(String clientId, String redirectUri) {
        boolean isValid = hasText(clientId) && hasText(redirectUri);
        if (!isValid) {
            throw new InvalidClientIdAndRedirectUriException(
                    clientId,
                    redirectUri,
                    "Required clientId and redirectUri."
            );
        }

        if (!validUriValidator.isValid(redirectUri)) {
            throw new InvalidRedirectUriException(redirectUri, "Invalid redirect uri.");
        }

        List<String> domains = allowDomainService.findDomainByClientId(clientId);
        if (isEmpty(domains)) {
            throw new InvalidClientIdException(clientId, "Empty allow domains.");
        }

        for (String domain : domains) {
            if (redirectUri.startsWith(domain)) {
                return;
            }
        }

        throw new InvalidClientIdAndRedirectUriException(
                clientId,
                redirectUri,
                "Invalid Domains."
        );
    }

    @Override
    public void verifyClientIdAndClientSecret(String clientId, String clientSecret) {
        boolean isValid = hasText(clientId) && hasText(clientSecret);
        if (!isValid) {
            throw new InvalidClientIdAndClientSecretException(
                    clientId,
                    clientSecret,
                    "Required clientId and clientSecret."
            );
        }

        String secret = clientService.findClientSecretByClientId(clientId);
        if (!hasText(secret)) {
            throw new InvalidClientIdException(clientId, "Empty clientSecret.");
        }

        if (!secret.equals(clientSecret)) {
            throw new InvalidClientIdAndClientSecretException(
                    clientId,
                    clientSecret,
                    "Invalid clientSecret."
            );
        }
    }

}
