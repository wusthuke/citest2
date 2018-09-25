/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 *
 * @author huke
 * @version $Id: IndexController.java, v 0.1 2018年09月17日 下午9:02 huke Exp $
 */
@RestController
@ApiIgnore
public class IndexController {

    @GetMapping("/abc/**")
    public String index() {
        return "index";
    }
}