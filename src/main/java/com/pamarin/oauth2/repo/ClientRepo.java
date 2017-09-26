/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.repo;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
public interface ClientRepo {

    String findClientSecretByClientId(String clientSecret);

}
