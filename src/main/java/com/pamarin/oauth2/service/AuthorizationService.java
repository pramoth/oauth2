/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import com.pamarin.oauth2.controller.AuthorizationRequest;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/25
 */
public interface AuthorizationService {

    String authorize(AuthorizationRequest authReq);

}
