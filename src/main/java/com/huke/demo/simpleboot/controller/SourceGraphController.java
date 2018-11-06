/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author huke
 * @version $Id: SourceGraphController.java, v 0.1 2018年10月29日 7:59 PM huke Exp $
 */
@RestController
public class SourceGraphController {

    @GetMapping(value = "/401/**")
    public void forbidden(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addHeader("Content-Type", "text/plain; charset=utf-8");
        response.addHeader("Connection", "keep-alive");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("WWW-Authenticate", "Basic realm=\"GitLab\"");
        response.addHeader("X-Frame-Options", "DENY");
        response.addHeader("X-Content-Type-Options", "nosniff");
        response.addHeader("X-Request-Id", "e33a8f0a-f7f3-4ecd-a3a2-22e30af88e17");
        response.addHeader("X-Runtime", "0.019904");
        response.addHeader("X-Ua-Compatible", "IE=edge");
        response.addHeader("X-Xss-Protection", "1; mode=block");

        response.setStatus(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        response.getWriter().write("HTTP Basic: Access denied");
        response.getWriter().flush();
    }

    @GetMapping("/kedou.hk/test.git/info/refs")
    public ResponseEntity<String> infoRefs(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Enumeration<String> requestHeaders = request.getHeaderNames();
        while (requestHeaders.hasMoreElements()) {
            String header = requestHeaders.nextElement();
            System.out.println(header + " : " + request.getHeader(header));
        }
        System.out.println("\n\n");

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Content-Type", "text/plain; charset=utf-8");
        headers.set("Connection", "keep-alive");
        headers.set("Cache-Control", "no-cache");
        headers.set("WWW-Authenticate", "Basic realm=\"GitLab\"");
        headers.set("X-Frame-Options", "DENY");
        headers.set("X-Content-Type-Options", "nosniff");
        headers.set("X-Request-Id", "e33a8f0a-f7f3-4ecd-a3a2-22e30af88e17");
        headers.set("X-Runtime", "0.019904");
        headers.set("X-Ua-Compatible", "IE=edge");
        headers.set("X-Xss-Protection", "1; mode=block");
        return new ResponseEntity("HTTP Basic: Access denied", headers, HttpStatus.UNAUTHORIZED);
    }
}