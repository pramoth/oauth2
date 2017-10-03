/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

import java.util.List;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/03
 */
public interface ScopeService {

    List<String> findByClientId(String clientId);

}
