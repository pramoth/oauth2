/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
public class RefreshAccessTokenRequest {

    private String grantType;

    private String refreshToken;

    private String redirectUri;

    private String clientId;

    private String clientSecret;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public static class Builder {

        private String grantType;

        private String refreshToken;

        private String redirectUri;

        private String clientId;

        private String clientSecret;

        public Builder setGrantType(String grantType) {
            this.grantType = grantType;
            return this;
        }

        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public RefreshAccessTokenRequest build() {
            RefreshAccessTokenRequest request = new RefreshAccessTokenRequest();
            request.setClientId(clientId);
            request.setClientSecret(clientSecret);
            request.setRefreshToken(refreshToken);
            request.setGrantType(grantType);
            request.setRedirectUri(redirectUri);
            return request;
        }

    }

}
