package com.mfz.prefsbuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mz
 * @date 2020/08/20/星期四
 * @time 22:44:25
 */
public class StringCodec {

    /**
     * 字符串解码方法
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    public @interface Decode {
        /**
         * 根据该id调用方法与{@link PrefsVal.String#codecId()}对应
         * 该id可以与{@link DefaultValue}重复
         * 注意1：该id应该与{@link Encode}成对出现，否则编码回出现问题
         * 注意2：仅限使用大于0的值
         *
         * @return the int
         */
        int id();
    }

    /**
     * 字符串的编码方法
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    public @interface Encode {
        /**
         * 根据该id调用方法与{@link PrefsVal.String#codecId()}对应
         * 该id可以与{@link DefaultValue}重复
         * 注意1：该id应该与{@link Decode}成对出现，否则解码会出现问题
         * 注意2：仅限使用大于0的值
         *
         * @return the int
         */
        int id();
    }
}
