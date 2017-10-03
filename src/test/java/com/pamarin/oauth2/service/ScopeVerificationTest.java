/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.exception.InvalidScopeException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/03
 */
public class ScopeVerificationTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private ScopeVerificationImpl scopeVerification;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldBeThrowInvalidScopeException_whenInputIsNull() {

        exception.expect(InvalidScopeException.class);
        exception.expectMessage("Required clientId and scope.");

        String clientId = null;
        String scope = null;

        scopeVerification.verifyByClientIdAndScope(clientId, scope);

    }

    @Test
    public void shouldBeThrowInvalidScopeException_whenInputIsEmptyString() {

        exception.expect(InvalidScopeException.class);
        exception.expectMessage("Required clientId and scope.");

        String clientId = "";
        String scope = "";

        scopeVerification.verifyByClientIdAndScope(clientId, scope);

    }

    @Test
    public void shouldBeOk_whenValidScope() {
        
        String clientId = "123456";
        String scope = "read";

        scopeVerification.verifyByClientIdAndScope(clientId, scope);

    }

}
