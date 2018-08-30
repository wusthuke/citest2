/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huke
 * @version $Id: CheckRunAnnotation.java, v 0.1 2018年08月28日 下午5:19 huke Exp $
 */
@Getter
@Setter
public class CheckRunAnnotation {

    private Long id;

    private Long checkRunId;

    private String path;

    private Integer startLine;

    private Integer endLine;

    private Integer startCol;

    private Integer endCol;

    private CheckRunAnnotationLevel level;

    private String title;

    private String message;

    private String details;
}