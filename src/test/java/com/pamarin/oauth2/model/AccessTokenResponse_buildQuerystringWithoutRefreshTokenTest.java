/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
public class AccessTokenResponse_buildQuerystringWithoutRefreshTokenTest {

    @Test
    public void shouldBeOk() {

        AccessTokenResponse input = new AccessTokenResponse.Builder()
                .setAccessToken("1234")
                .setExpiresIn(3600)
                .setRefreshToken("5678")
                .setState("XYZ")
                .setTokenType("bearer")
                .build();

        String output = input.buildQuerystringWithoutRefreshToken();
        String expected = "access_token=1234&state=XYZ&token_type=bearer&expires_in=3600";

        assertThat(output).isEqualTo(expected);
    }

}
