package com.mfz.prefsbuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字符串解码方法
 *
 * @author mz
 * @date 2020 /05/20/Wed
 * @time 10 :53
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface StringDecode {
    /**
     * 根据该id调用方法与{@link PrefsVal.String#codecId()}对应
     * 该id可以与{@link DefaultValue}重复
     * 注意1：该id应该与{@link StringEncode}成对出现，否则编码回出现问题
     * 注意2：请勿使用0或-1
     *
     * @return the int
     */
    int id();
}
