/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
public class CodeAccessTokenRequest {

    private String grantType;

    private String code;

    private String redirectUri;

    private String clientId;

    private String clientSecret;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

        private String code;

        private String redirectUri;

        private String clientId;

        private String clientSecret;

        public Builder setGrantType(String grantType) {
            this.grantType = grantType;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
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

        public CodeAccessTokenRequest build() {
            CodeAccessTokenRequest request = new CodeAccessTokenRequest();
            request.setClientId(clientId);
            request.setClientSecret(clientSecret);
            request.setCode(code);
            request.setGrantType(grantType);
            request.setRedirectUri(redirectUri);
            return request;
        }

    }

}
