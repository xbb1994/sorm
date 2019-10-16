package com.nt.sorm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 标记主键，如果没有加上该注解，会默认表的主键为rowguid
 * @author: Xu
 * @create: 2019-05-13 15:14
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PK {
}
