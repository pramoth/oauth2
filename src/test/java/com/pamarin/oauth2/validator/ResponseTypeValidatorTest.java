/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.validator;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/03
 */
public class ResponseTypeValidatorTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private ResponseTypeValidator validator;

    @Before
    public void before() {
        validator = new ResponseTypeValidatorImpl();
    }

    @Test
    public void shouldBeFalse_whenInputIsNull() {
        String input = null;
        boolean output = validator.isValid(input);
        boolean expected = false;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeFalse_whenInputIsEmptyString() {
        String input = "";
        boolean output = validator.isValid(input);
        boolean expected = false;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeFalse_whenInputIsAAA() {
        String input = "AAA";
        boolean output = validator.isValid(input);
        boolean expected = false;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeTrue_whenInputIsCode() {
        String input = "code";
        boolean output = validator.isValid(input);
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }

    @Test
    public void shouldBeTrue_whenInputIsToken() {
        String input = "token";
        boolean output = validator.isValid(input);
        boolean expected = true;
        assertThat(output).isEqualTo(expected);
    }

}
