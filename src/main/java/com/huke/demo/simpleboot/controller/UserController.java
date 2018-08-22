/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author huke
 * @version $Id: UserController.java, v 0.1 2018年08月21日 下午5:14 huke Exp $
 */
@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping("{id}")
    public Map get(@PathVariable("id") long userId) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", String.valueOf(userId));
        userMap.put("name", "huke");

        return userMap;
    }
}