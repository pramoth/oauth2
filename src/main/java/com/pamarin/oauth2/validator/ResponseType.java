/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/03
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ResponseType.Validator.class)
public @interface ResponseType {

    @Component
    public static class Validator implements ConstraintValidator<ResponseType, String> {

        @Override
        public void initialize(ResponseType a) {

        }

        @Override
        public boolean isValid(String type, ConstraintValidatorContext context) {
            if (!hasText(type)) {
                return false;
            }

            return "code".equals(type)
                    || "token".equals(type);
        }

        public boolean isValid(String type) {
            return isValid(type, null);
        }

    }

}
