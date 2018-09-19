/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author huke
 * @version $Id: UserNotFoundException.java, v 0.1 2018年09月18日 下午3:31 huke Exp $
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No Such User")
public class UserNotFoundException extends RuntimeException {


}