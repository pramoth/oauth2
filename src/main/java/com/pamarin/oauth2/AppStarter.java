/*
 * Copy right 2017 Pamarin.com
 */

package com.pamarin.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/24
 */
@ComponentScan("com.pamarin.oauth2")
@EnableAutoConfiguration
public class AppStarter {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppStarter.class, args);
    }

}
