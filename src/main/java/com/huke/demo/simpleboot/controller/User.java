/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author huke
 * @version $Id: User.java, v 0.1 2018年08月28日 下午7:47 huke Exp $
 */
@Getter
@Setter
public class User {

    private String name;

    private Sex sex;

    List<CheckRunAnnotation> annotations;

    enum Sex {
        man,

        woman
    }
}