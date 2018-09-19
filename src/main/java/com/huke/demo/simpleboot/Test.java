/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.huke.demo.simpleboot.controller.User;

import java.lang.annotation.Annotation;

/**
 *
 * @author huke
 * @version $Id: Test.java, v 0.1 2018年09月19日 上午10:43 huke Exp $
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = generatorMapper();

        User user = new User();
        user.setName("hello");
        user.setSex(User.Sex.man);
        user.setAddress("hello");
        System.out.println(objectMapper.writeValueAsString(user));
    }

    public static ObjectMapper generatorMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        objectMapper.registerModule(module);

        objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {

            @Override
            public boolean isAnnotationBundle(Annotation ann) {
                if (ann.annotationType() == JSONField.class) {
                    return true;
                }
                return super.isAnnotationBundle(ann);
            }

            @Override
            public PropertyName findNameForSerialization(Annotated a) {
                PropertyName nameForSerialization = super.findNameForSerialization(a);
                if (nameForSerialization == null || nameForSerialization == PropertyName.USE_DEFAULT) {
                    JSONField jsonField = _findAnnotation(a, JSONField.class);
                    if (jsonField != null) {
                        return PropertyName.construct(jsonField.name());
                    }
                }
                return nameForSerialization;
            }

            @Override
            public PropertyName findNameForDeserialization(Annotated a) {
                PropertyName nameForDeserialization = super.findNameForDeserialization(a);
                if (nameForDeserialization == null || nameForDeserialization == PropertyName.USE_DEFAULT) {
                    JSONField jsonField = _findAnnotation(a, JSONField.class);
                    if (jsonField != null) {
                        return PropertyName.construct(jsonField.name());
                    }
                }
                return nameForDeserialization;
            }
        });

        return objectMapper;
    }
}

