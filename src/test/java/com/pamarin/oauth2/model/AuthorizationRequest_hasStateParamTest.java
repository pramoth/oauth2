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
public class AuthorizationRequest_hasStateParamTest {

    @Test
    public void shouldBeFalse_whenResponseTypeIsTokenAndStateIsXYZ() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("token")
                .setState("XYZ")
                .build();
        boolean output = input.hasStateParam();
        boolean expected = false;
        assertThat(output).isEqualTo(expected);
    }
    
    @Test
    public void shouldBeTrue_whenResponseTypeIsCodeAndStateIsXYZ() {
        AuthorizationRequest input = new AuthorizationRequest.Builder()
                .setResponseType("code")
                .setState("XYZ")
                .build();
        boolean output = input.hasStateParam();
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }

}
