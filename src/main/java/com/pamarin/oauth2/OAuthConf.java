/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2;

import com.pamarin.oauth2.service.LoginSession;
import com.pamarin.oauth2.model.AccessTokenResponse;
import com.pamarin.oauth2.model.AuthorizationRequest;
import com.pamarin.oauth2.model.AuthorizationResponse;
import com.pamarin.oauth2.model.CodeAccessTokenRequest;
import com.pamarin.oauth2.model.RefreshAccessTokenRequest;
import com.pamarin.oauth2.service.AccessTokenGenerator;
import com.pamarin.oauth2.service.AllowDomainService;
import com.pamarin.oauth2.service.AuthorizationCodeGenerator;
import com.pamarin.oauth2.service.ClientService;
import com.pamarin.oauth2.service.ScopeService;
import com.pamarin.oauth2.service.ScopeVerification;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
@Configuration
public class OAuthConf {
    
    @Bean ScopeService newScopeService(){
        return (clientId) -> Arrays.asList("read");
    }

    @Bean
    public ClientService newClientService() {
        return (clientId) -> "password";
    }

    @Bean
    public AllowDomainService newAllowDomainService() {
        return (clientId) -> Arrays.asList("http://localhost");
    }

    @Bean
    public LoginSession newLoginSession() {
        return () -> false;
    }

    @Bean
    public AuthorizationCodeGenerator newAuthorizationCodeGenerator() {
        return (authReq) -> new AuthorizationResponse.Builder()
                .setCode(UUID.randomUUID().toString())
                .build();
    }

    @Bean
    public AccessTokenGenerator newAccessTokenGenerator() {
        return new AccessTokenGenerator() {

            private AccessTokenResponse newAccessTokenResponse() {
                return new AccessTokenResponse.Builder()
                        .setAccessToken(UUID.randomUUID().toString())
                        .setExpiresIn(3600L)
                        .setRefreshToken(UUID.randomUUID().toString())
                        .setTokenType("bearer")
                        .build();
            }

            @Override
            public AccessTokenResponse generate(AuthorizationRequest request) {
                return newAccessTokenResponse();
            }

            @Override
            public AccessTokenResponse generate(CodeAccessTokenRequest request) {
                return newAccessTokenResponse();
            }

            @Override
            public AccessTokenResponse generate(RefreshAccessTokenRequest request) {
                return newAccessTokenResponse();
            }
        };
    }

}
