/*
 * Copy right 2017 Pamarin.com
 */

package com.pamarin.oauth2.controller;

/**
 * @author jittagornp <http://jittagornp.me>  
 * create : 2017/09/25
 */
public interface ClientVerification {

    void verifyClientIdAndRedirectUri(String clinentId, String redirectUri);
    
}
