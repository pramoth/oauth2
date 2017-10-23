/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/22
 */
public class QuerystringBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(QuerystringBuilder.class);

    private Map<String, String> params;

    private Map<String, String> getParams() {
        if (params == null) {
            params = new LinkedHashMap<>();
        }
        return params;
    }

    public QuerystringBuilder addParameter(String name, String value) {
        if (hasText(value)) {
            getParams().put(name, value);
        }
        return this;
    }

    private String encode(String url) {
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException ex) {
            LOG.warn("Unsupported encoding", ex);
            return null;
        }
    }

    public String build() {
        return getParams().entrySet()
                .stream()
                .map(param -> {
                    return param.getKey() + "=" + encode(param.getValue());
                }).collect(Collectors.joining("&"));
    }
}
