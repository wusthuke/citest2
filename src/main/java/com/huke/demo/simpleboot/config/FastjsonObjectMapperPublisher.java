/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.config;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.configuration.ObjectMapperConfigured;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;

/**
 *
 * @author huke
 * @version $Id: FastjsonObjectMapperPublisher.java, v 0.1 2018年09月19日 上午11:35 huke Exp $
 */
@Component
@DependsOn("requestMappingHandlerAdapter")
public class FastjsonObjectMapperPublisher implements InitializingBean, Ordered {

    private ObjectMapperConfigured objectMapperConfigured;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void setObjectMapper() {
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

        this.objectMapperConfigured = new ObjectMapperConfigured(applicationContext, objectMapper);
        applicationContext.publishEvent(objectMapperConfigured);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.publishEvent(objectMapperConfigured);
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}