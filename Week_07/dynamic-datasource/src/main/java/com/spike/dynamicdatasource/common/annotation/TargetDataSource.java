package com.spike.dynamicdatasource.common.annotation;

import java.lang.annotation.*;

import com.spike.dynamicdatasource.common.constant.RoutingConstant;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    String name() default TargetDataSource.PRIMARY;
    
    String group();

    public static String PRIMARY = RoutingConstant.PRIMARY;

    public static String REPLICA = RoutingConstant.REPLICA;
}