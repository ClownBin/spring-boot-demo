package com.example.demo.annotation.aop;

import java.lang.annotation.*;

/**
 * Check cluster role.
 * Example:
 * \@RoleCheck(#creatorId)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleCheck {

    /**
     * 0. 集群管理员， 1.集群普通用户
     * 传入用户id
     */
    String creatorId() default "";

    /**
     * 集群id
     */
    String clusterId() default "";

}
