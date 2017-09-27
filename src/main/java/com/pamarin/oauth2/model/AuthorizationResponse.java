/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
public class AuthorizationResponse {

    private String code;

    private String state;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String buildQuerystring() {
        StringBuilder builder = new StringBuilder();
        builder.append("code=")
                .append(code);
        if (hasText(state)) {
            builder.append("&state=")
                    .append(state);
        }
        return builder.toString();
    }

    public static class Builder {

        private String code;

        private String state;

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public AuthorizationResponse build() {
            AuthorizationResponse response = new AuthorizationResponse();
            response.setCode(code);
            response.setState(state);
            return response;
        }

    }
}
