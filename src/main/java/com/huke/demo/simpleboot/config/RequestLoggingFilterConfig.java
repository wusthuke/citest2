/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * @author huke
 * @version $Id: RequestLoggingFilterConfig.java, v 0.1 2018年08月22日 下午8:49 huke Exp $
 */
// @Configuration
public class RequestLoggingFilterConfig {

    /*private @Autowired
    CommonsRequestLoggingFilter loggingFilter;*/

    // @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(true);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

    /*@Bean
    public FilterRegistrationBean<CommonsRequestLoggingFilter> loggingFilter() {
        FilterRegistrationBean<CommonsRequestLoggingFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(this.loggingFilter);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }*/
}