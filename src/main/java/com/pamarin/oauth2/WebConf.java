/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
@Configuration
@EnableWebMvc
public class WebConf extends WebMvcConfigurerAdapter {

    @Bean
    public MethodValidationPostProcessor newMethodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
