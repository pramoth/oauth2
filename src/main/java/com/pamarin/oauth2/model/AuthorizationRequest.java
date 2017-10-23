/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import com.pamarin.oauth2.util.QuerystringBuilder;
import static org.springframework.util.StringUtils.hasText;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/24
 */
public class AuthorizationRequest {

    private String responseType;
    private String clientId;
    private String redirectUri;
    private String scope;
    private String state;

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isValidRequest() {
        return hasText(responseType)
                && hasText(clientId)
                && hasText(redirectUri);
    }

    public boolean haveSomeParameters() {
        return hasText(responseType)
                || hasText(clientId)
                || hasText(redirectUri)
                || hasText(scope)
                || hasText(state);
    }

    public void validateParameters() throws MissingServletRequestParameterException {
        if (haveSomeParameters()) {
            if (!hasText(responseType)) {
                throw new MissingServletRequestParameterException("response_type", "String");
            }

            if (!hasText(clientId)) {
                throw new MissingServletRequestParameterException("client_id", "String");
            }

            if (!hasText(redirectUri)) {
                throw new MissingServletRequestParameterException("redirect_uri", "String");
            }
        }
    }

    public boolean responseTypeIsCode() {
        return "code".equals(responseType);
    }

    public boolean responseTypeIsToken() {
        return "token".equals(responseType);
    }

    public boolean hasStateParam() {
        return hasText(state);
    }

    public String buildQuerystring() {
        return new QuerystringBuilder()
                .addParameter("response_type", getResponseType())
                .addParameter("client_id", getClientId())
                .addParameter("redirect_uri", getRedirectUri())
                .addParameter("scope", getScope())
                .addParameter("state", getState())
                .build();
    }

    public static class Builder {

        private String responseType;
        private String clientId;
        private String redirectUri;
        private String scope;
        private String state;

        public Builder setResponseType(String responseType) {
            this.responseType = responseType;
            return this;
        }

        public Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder setScope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public AuthorizationRequest build() {
            AuthorizationRequest request = new AuthorizationRequest();
            request.setClientId(clientId);
            request.setRedirectUri(redirectUri);
            request.setResponseType(responseType);
            request.setScope(scope);
            request.setState(state);
            return request;
        }
    }

}
