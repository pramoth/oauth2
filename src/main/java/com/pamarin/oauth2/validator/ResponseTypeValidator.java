/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.validator;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/03
 */
public interface ResponseTypeValidator {

    boolean isValid(String responseType);

}
