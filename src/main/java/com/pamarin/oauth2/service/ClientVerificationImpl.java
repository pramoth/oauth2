/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidClientIdAndRedirectUriException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
@Service
public class ClientVerificationImpl implements ClientVerification {

    private List<String> allowDomains;

    public void setAllowDomains(List<String> allowDomains) {
        this.allowDomains = allowDomains;
    }

    public List<String> getAllowDomains() {
        if (allowDomains == null) {
            allowDomains = new ArrayList<>();
        }

        return allowDomains;
    }

    @Override
    public void verifyClientIdAndRedirectUri(String clientId, String redirectUri) {
        boolean isValid = hasText(clientId) && hasText(redirectUri);
        if (!isValid) {
            throw new InvalidClientIdAndRedirectUriException(clientId, redirectUri);
        }

        List<String> domains = getAllowDomains();
        for (String domain : domains) {
            if (redirectUri.startsWith(domain)) {
                return;
            }
        }

        throw new InvalidClientIdAndRedirectUriException(clientId, redirectUri);
    }

}
