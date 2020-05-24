package com.mfz.prefsbuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基类的注解
 * 需要有一个构造方法有且仅有一个参数，类型为{@link String}
 *
 * @author mz
 * @date 2020 /05/19/Tue
 * @time 16 :09
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface BasePrefsClass {
}
