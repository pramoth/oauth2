/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
public class ErrorResponse {

    private String error;

    @JsonProperty("error_description")
    private String errorDescription;

    @JsonProperty("error_uri")
    private String errorUri;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorUri() {
        return errorUri;
    }

    public void setErrorUri(String errorUri) {
        this.errorUri = errorUri;
    }

}
