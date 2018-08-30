/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller;

/**
 * @author huke
 * @version $Id: CheckRunAnnotationLevel.java, v 0.1 2018年08月28日 下午5:21 huke Exp $
 */
public enum CheckRunAnnotationLevel {

    Failure(0),

    Warning(1),

    Notice(2);

    private int value;

    CheckRunAnnotationLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CheckRunAnnotationLevel fromValue(int value) {
        for (CheckRunAnnotationLevel sourceType : CheckRunAnnotationLevel.values()) {
            if (sourceType.value == value) {
                return sourceType;
            }
        }
        throw new IllegalArgumentException("There is no value " + value + " in CheckRunAnnotationLevel enum");
    }
}