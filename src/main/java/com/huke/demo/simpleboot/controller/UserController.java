/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller;

import com.huke.demo.simpleboot.controller.exception.UserNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author huke
 * @version $Id: UserController.java, v 0.1 2018年08月21日 下午5:14 huke Exp $
 */
@RestController
@RequestMapping("users")
@Slf4j
@Api(description = "用户管理")
public class UserController {

    @ApiOperation(value = "获取用户列表", notes = "获取所有用户信息")
    @GetMapping("{id}")
    public User get(@ApiParam("用户ID") @PathVariable("id") long userId) {
        User user = new User();
        user.setName("user" + userId);
        user.setSex(User.Sex.man);
        user.setAddress("hello");
        return user;
    }

    @GetMapping("/not")
    public String notFound() {
        throw new UserNotFoundException();
    }

    @PostMapping("/webhook")
    public Map create(@RequestHeader Map headers, @RequestBody Map<String, Object> params) {
        return params;
    }

    @ApiIgnore
    @PostMapping
    public String createUser(@RequestBody User user) {

        return user.getName() + " : " + user.getSex();
    }
}