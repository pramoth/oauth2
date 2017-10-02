/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.validator;

import org.springframework.stereotype.Component;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/03
 */
@Component
public class ResponseTypeValidatorImpl implements ResponseTypeValidator {

    @Override
    public boolean isValid(String responseType) {
        if (!hasText(responseType)) {
            return false;
        }

        return "code".equals(responseType)
                || "token".equals(responseType);
    }

}
