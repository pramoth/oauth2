/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
public class AuthorizationRequest_responseTypeIsXXXTest {

    @Test
    public void shouldBeFalse_whenResponseTypeIsAAA() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("AAA")
                .build();
        boolean output = input.responseTypeIsCode();
        boolean expected = false;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeTrue_whenResponseTypeIsCode() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .build();
        boolean output = input.responseTypeIsCode();
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeFalse_whenResponseTypeIsBBB() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("BBB")
                .build();
        boolean output = input.responseTypeIsToken();
        boolean expected = false;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBTrue_whenResponseTypeIsToken() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("token")
                .build();
        boolean output = input.responseTypeIsToken();
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }

}
