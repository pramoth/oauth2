/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.util;

import com.pamarin.oauth2.exception.InvalidHttpBasicAuthenException;
import com.pamarin.oauth2.util.HttpBasicAuthenParser.Output;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
public class HttpBasicAuthenParserTest {

    private HttpBasicAuthenParser parser;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        parser = new HttpBasicAuthenParserImpl();
    }

    @Test
    public void shouldBeRequiredAuthorization_whenInputIsNull() {

        exception.expect(InvalidHttpBasicAuthenException.class);
        exception.expectMessage("Required authorization.");

        String input = null;
        Output output = parser.parse(input);

    }

    @Test
    public void shouldBeRequiredAuthorization_whenInputIsEmptyString() {

        exception.expect(InvalidHttpBasicAuthenException.class);
        exception.expectMessage("Required authorization.");

        String input = "";
        Output output = parser.parse(input);

    }

    @Test
    public void shouldBeInvalidCredentialValue_whenInputIsXXX() {

        exception.expect(InvalidHttpBasicAuthenException.class);
        exception.expectMessage("Invalid Credential value (Required Basic Authen).");

        String input = "XXX";
        Output output = parser.parse(input);

    }

    @Test
    public void shouldBeInvalidCredentialValue_whenInputIsBasicEmptyString() {

        exception.expect(InvalidHttpBasicAuthenException.class);
        exception.expectMessage("Invalid Credential value (Empty value).");

        String input = "Basic ";
        Output output = parser.parse(input);

    }

    @Test
    public void shouldBeInvalidCredentialValue_whenInputIsBasicDGVzdA() {

        exception.expect(InvalidHttpBasicAuthenException.class);
        exception.expectMessage("Invalid Credential value (Can't decode base64).");

        String input = "Basic dGVzdA=";
        Output output = parser.parse(input);

    }

    @Test
    public void shouldBeInvalidCredentialValue_whenInputIsBasicMTIzNA() {

        exception.expect(InvalidHttpBasicAuthenException.class);
        exception.expectMessage("Invalid Credential value (Don't have semicolon between username/password).");

        String input = "Basic MTIzNA==";
        Output output = parser.parse(input);

    }

    @Test
    public void shouldBeOk_whenInputIsBasicDGVzdDoxMjM0NTY() {

        String input = "Basic dGVzdDoxMjM0NTY=";
        Output output = parser.parse(input);
        Output expected = new Output("test", "123456");

        assertThat(output).isEqualTo(expected);
    }
    
     @Test
    public void shouldBeOk_whenInputIsBasicDGVzdDowMDAw_caseInsensitive() {

        String input = "basic dGVzdDowMDAw";
        Output output = parser.parse(input);
        Output expected = new Output("test", "0000");

        assertThat(output).isEqualTo(expected);
    }
}
