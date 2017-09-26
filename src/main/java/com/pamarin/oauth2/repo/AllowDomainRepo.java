/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.repo;

import java.util.List;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
public interface AllowDomainRepo {

    List<String> findDomainByClientId(String clientId);

}
