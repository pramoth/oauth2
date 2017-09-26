/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
public class AccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expries_in")
    private long expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static class Builder {

        private String accessToken;

        private String tokenType;

        private long expiresIn;

        private String refreshToken;

        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder setTokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public Builder setExpiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }

        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }
        
        public AccessTokenResponse build(){
            AccessTokenResponse response = new AccessTokenResponse();
            response.setAccessToken(accessToken);
            response.setExpiresIn(expiresIn);
            response.setRefreshToken(refreshToken);
            response.setTokenType(tokenType);
            return response;
        }

    }
}
