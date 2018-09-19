/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import sun.jvm.hotspot.debugger.Address;

/**
 * @author huke
 * @version $Id: User.java, v 0.1 2018年08月28日 下午7:47 huke Exp $
 */
@Getter
@Setter
public class User {

    @JSONField(name = "user_name")
    private String name;

    private Sex sex;

    @JsonProperty(value = "user_address")
    private String address;

    public enum Sex {
        man,

        woman
    }
}