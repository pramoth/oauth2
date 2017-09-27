/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import static org.springframework.util.StringUtils.hasText;

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
        if (!hasText(scope)) {
            scope = "read";
        }
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
        StringBuilder builder = new StringBuilder()
                .append("response_type=")
                .append(responseType)
                .append("&client_id=")
                .append(clientId)
                .append("&redirect_uri=")
                .append(redirectUri)
                .append("&scope=")
                .append(getScope());

        if (hasStateParam()) {
            builder.append("&state=")
                    .append(state);
        }

        return builder.toString();
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
