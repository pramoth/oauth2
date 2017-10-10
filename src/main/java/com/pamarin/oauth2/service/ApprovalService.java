/*
 * Copyright 2017 Pamarin.com
 */

package com.pamarin.oauth2.service;

/**
 * @author jittagornp <http://jittagornp.me>  
 * create : 2017/10/10
 */
public interface ApprovalService {

    boolean wasApprovedByUserIdAndClientId(Long userId, String clientId);
    
}
