package com.baizhi.bts.conf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//什么时候执行
@Target({ElementType.METHOD})//运行未知
public @interface MyLog {
    String value();
}
