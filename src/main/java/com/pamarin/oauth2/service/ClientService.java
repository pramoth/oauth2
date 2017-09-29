/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.service;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/29
 */
public interface ClientService {

    String findClientSecretByClientId(String clientId);

}
