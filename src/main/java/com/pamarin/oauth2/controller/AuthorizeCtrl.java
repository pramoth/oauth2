/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import static org.springframework.util.StringUtils.hasText;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/24
 */
@Controller
public class AuthorizeCtrl {

    @Autowired
    private ClientVerification clientVerification;

    @Autowired
    private LoginSession loginSession;

    private String getHostUrl() {
        return "";
    }

    private String generateCode(AuthorizaionRequest authReq) {
        return null;
    }

    private String generateAccessToken(AuthorizaionRequest authReq) {
        return null;
    }

    @GetMapping(value = "/api/v1/oauth/authorize")
    public void authorize(
            @RequestParam("response_type") String responseType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam(value = "scope", required = false, defaultValue = "read") String scope,
            @RequestParam(name = "state", required = false) String state,
            HttpServletResponse resp
    ) throws IOException {
        clientVerification.verifyClientIdAndRedirectUri(clientId, redirectUri);
        AuthorizaionRequest authReq = new AuthorizaionRequest.Builder()
                .setClientId(clientId)
                .setRedirectUri(redirectUri)
                .setResponseType(responseType)
                .setScope(scope)
                .setState(state)
                .build();

        if (loginSession.wasCreated()) {
            obtainingAuthorization(authReq, resp);
        } else {
            redirect2Login(authReq, resp);
        }
    }

    private void obtainingAuthorization(AuthorizaionRequest authReq, HttpServletResponse resp) throws IOException {
        if (authReq.responseTypeIsCode()) {
            generateAuthorizationCode(authReq, resp);
            return;
        }

        if (authReq.responseTypeIsToken()) {
            String token = generateAccessToken(authReq);
            resp.sendRedirect(authReq.getRedirectUri() + "#token=" + token);
            return;
        }

        throw new UnsupportedOperationException("Unsuported response_type=" + authReq.getResponseType());
    }

    private void generateAuthorizationCode(AuthorizaionRequest authReq, HttpServletResponse resp) throws IOException {
        String code = generateCode(authReq);
        String uri = authReq.getRedirectUri();
        StringBuilder builder = new StringBuilder()
                .append(uri)
                .append(uri.contains("?") ? "&" : "?")
                .append("code=")
                .append(code);

        if ("code".equals(authReq.getResponseType()) && hasText(authReq.getState())) {
            builder.append("&state=")
                    .append(authReq.getState());
        }

        resp.sendRedirect(builder.toString());
    }

    private void redirect2Login(AuthorizaionRequest authReq, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(getHostUrl() + "/login?" + authReq.toString());
    }
}
