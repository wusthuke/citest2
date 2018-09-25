/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huke
 * @version $Id: User.java, v 0.1 2018年08月28日 下午7:47 huke Exp $
 */
@Getter
@Setter
public class User {

    @ApiModelProperty("姓名")
    @JSONField(name = "user_name")
    private String name;

    @ApiModelProperty("性别")
    private Sex sex;

    @ApiModelProperty("用户地址")
    @JsonProperty(value = "user_address")
    private String address;

    public enum Sex {
        man,

        woman
    }
}