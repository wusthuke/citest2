/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author huke
 * @version $Id: GitlabController.java, v 0.1 2018年08月30日 上午11:53 huke Exp $
 */
@Slf4j
@RestController
public class GitlabController {

    @PostMapping
    public String webhook(@RequestBody Map params) {
        log.info("Gitlab Webhook : {}", params);
        return "SUCCESS";
    }
}