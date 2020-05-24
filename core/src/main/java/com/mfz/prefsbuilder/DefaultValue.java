package com.mfz.prefsbuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 仅用在静态方法或者常量上
 *
 * @author mz
 * @date 2020 /05/15/Fri
 * @time 17 :21
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DefaultValue {
    /**
     * 根据与指定类型的defValFromId匹配来获取常量或者方法
     * 注意：请勿使用0
     *
     * @return the int
     */
    int id();
}
