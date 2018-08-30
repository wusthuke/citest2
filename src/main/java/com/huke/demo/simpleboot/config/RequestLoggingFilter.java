/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author huke
 * @version $Id: RequestLoggingFilter.java, v 0.1 2018年08月23日 下午5:26 huke Exp $
 */
@Order(Ordered.LOWEST_PRECEDENCE - 8)
@Slf4j
// @Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        wrappedRequest.getInputStream();

        Map<String, Object> trace = getTrace(wrappedRequest, 0);
        getBody(wrappedRequest, trace);
        this.logTrace(request, trace);

        filterChain.doFilter(wrappedRequest, response);
    }

    private String logTrace(HttpServletRequest request, Map<String, Object> trace) {
        StringBuffer stringBuffer = new StringBuffer("");
        if (Objects.nonNull(trace.get("method"))) {
            stringBuffer.append("method: ").append(trace.get("method")).append("\n");
        }
        if (Objects.nonNull(trace.get("path"))) {
            stringBuffer.append("path: ").append(trace.get("path")).append("\n");
        }
        if (Objects.nonNull(trace.get("body"))) {
            stringBuffer.append("body: ").append(trace.get("body")).append("\n");
        }
        log.info(stringBuffer.toString());
        return stringBuffer.toString();
    }

    private void getBody(ContentCachingRequestWrapper request, Map<String, Object> trace) {
        // wrap request to make sure we can read the body of the request (otherwise it will be consumed by the actual
        // request handler)
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    payload = "[unknown]";
                }

                trace.put("body", payload);
            }
        }
    }

    protected Map<String, Object> getTrace(HttpServletRequest request, int status) {
        Principal principal = request.getUserPrincipal();

        Map<String, Object> trace = new LinkedHashMap<String, Object>();
        trace.put("method", request.getMethod());
        trace.put("path", request.getRequestURI());
        // trace.put("principal", principal.getName());
        trace.put("query", request.getQueryString());
        trace.put("statusCode", status);

        return trace;
    }
}