package com.mfz.prefsbuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 根据注解的类型创建prefs类中的get/is/set方法
 * 注意1：类需要注解{@link PrefsClass}
 * 注意2：需要有类实现{@link BasePrefsInterface}并注解{@link BasePrefsClass}
 * 注意3：仅支持以下值，部分类型需要定义序列化与反序列化方法
 *
 * @author mz
 * @date 2020 /05/14/Thu
 * @time 14 :56
 */
public class PrefsVal {

    /**
     * int类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Int {
        /**
         * 默认值的方法
         * 优先级高
         * 仅限使用大于0的值
         *
         * @return the int
         */
        int defValFromId() default 0;

        /**
         * 默认值
         *
         * @return the int
         */
        int defVal() default 0;

        /**
         * key的前缀class类型
         * 注意：使用的是该类的{@link java.lang.Object#toString()}方法
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * key的后缀class类型.
         * 注意：使用的是该类的{@link java.lang.Object#toString()}方法
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * float类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Float {
        /**
         * Def val from id int.
         *
         * @return the int
         */
        int defValFromId() default 0;

        /**
         * Def val float.
         *
         * @return the float
         */
        float defVal() default 0f;

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * boolean类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Bool {
        /**
         * Def val from id int.
         *
         * @return the int
         */
        int defValFromId() default 0;

        /**
         * Def val boolean.
         *
         * @return the boolean
         */
        boolean defVal() default false;

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * byte类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Byte {
        /**
         * Def val from id int.
         *
         * @return the int
         */
        int defValFromId() default 0;

        /**
         * Def val byte.
         *
         * @return the byte
         */
        byte defVal() default 0;

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * double类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Double {
        /**
         * Def val from id int.
         *
         * @return the int
         */
        int defValFromId() default 0;

        /**
         * Def val double.
         *
         * @return the double
         */
        double defVal() default 0.0;

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * char类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Char {
        /**
         * Def val from id int.
         *
         * @return the int
         */
        int defValFromId() default 0;

        /**
         * Def val char.
         *
         * @return the char
         */
        char defVal() default '\0';

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * long类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Long {
        /**
         * Def val from id int.
         *
         * @return the int
         */
        int defValFromId() default 0;

        /**
         * Def val long.
         *
         * @return the long
         */
        long defVal() default 0L;

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * short类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Short {
        /**
         * Def val from id int.
         *
         * @return the int
         */
        int defValFromId() default 0;

        /**
         * Def val short.
         *
         * @return the short
         */
        short defVal() default 0;

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * String类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface String {

        /**
         * 默认值从该id关联的注解静态方法中获取，该静态方法最多仅能有一个参数，
         * 返回的对象必须是String
         * 大于0的值才有效
         *
         * @return 关联的id int
         */
        int defValFromId() default 0;

        /**
         * 默认值
         * 优先级最低
         *
         * @return the java . lang . string
         */
        java.lang.String defVal() default "";

        /**
         * 默认值是否为null，若为true，则其他的默认值无效
         *
         * @return 是否为null boolean
         */
        boolean defNull() default true;

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;

        /**
         * 编解码方法id，string特有参数
         * 注意：编解码的方法应该共用一个id，若不成对出现将会出错
         *
         * @return the int
         */
        int codecId() default 0;
    }

    /**
     * 除基本类型以外的类型保存，包括基本类型的装箱类
     * 注意：需要设置序列号和反序列化方法，否则将会出错
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Object {

        /**
         * 返回值类型
         *
         * @return the class
         */
        Class<?> type();

        /**
         * 默认值从该key关联的注解静态方法中获取，该静态方法最多仅能有一个参数，
         * 返回的对象必须是该类型{@link #type()}
         *
         * @return 关联的id int
         */
        int defValFromId() default 0;

        /**
         * 默认值是否为null，当为true时，那么其他默认值无效
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defNull() default true;

        /**
         * 默认的string
         *
         * @return 字符串 java . lang . string
         */
        java.lang.String defString() default "";

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * {@link java.util.List}类型
     * 注意：需要设置序列化和反序列化方法(序列化可使用object的)
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface List {

        /**
         * 返回值类型
         *
         * @return the class
         */
        Class<?> type();

        /**
         * 默认值从该key关联的注解静态方法或常量中获取，若为静态方法，则最多仅能有一个参数，
         * 返回的对象必须是该类型{@link #type()}
         *
         * @return 关联的key int
         */
        int defValFromId() default 0;

        /**
         * 默认值是否为null，当为true时，那么其他默认值无效
         * {@link #defEmpty()} ()}
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defNull() default false;

        /**
         * 默认值是否为空列表，
         * 优先级低于{@link #defNull()}
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defEmpty() default true;

        /**
         * 默认的string
         *
         * @return 字符串 java . lang . string
         */
        java.lang.String defString() default "";

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * {@link java.util.Set}类型
     * 需要设置序列化和反序列化方法(序列化可使用object的)
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Set {

        /**
         * 返回值类型
         *
         * @return the class
         */
        Class<?> type();

        /**
         * 默认值从该key关联的注解静态方法或常量中获取，若为静态方法，则最多仅能有一个参数，
         * 返回的对象必须是该类型{@link #type()}
         *
         * @return 关联的id int
         */
        int defValFromId() default 0;

        /**
         * 默认值是否为null，当为true时，那么其他默认值无效
         * {@link #defValFromId()}
         * {@link #defEmpty()} ()}
         *
         * @return 是否为null boolean
         */
        boolean defNull() default false;

        /**
         * 默认值是否为空列表，
         * 优先级低于{@link #defNull()}
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defEmpty() default true;

        /**
         * 默认的string
         *
         * @return 字符串 java . lang . string
         */
        java.lang.String defString() default "";

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * {@link java.util.Queue}类型
     * 需要设置序列化和反序列化方法(序列化可使用object的)
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Queue {

        /**
         * 返回值类型
         *
         * @return the class
         */
        Class<?> type();

        /**
         * 默认值从该key关联的注解静态方法或常量中获取，若为静态方法，则最多仅能有一个参数，
         * 返回的对象必须是该类型{@link #type()}
         *
         * @return 关联的id int
         */
        int defValFromId() default 0;

        /**
         * 默认值是否为null，当为true时，那么其他默认值无效
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defNull() default false;

        /**
         * 默认值是否为空列表，
         * 优先级低于{@link #defNull()}
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defEmpty() default true;

        /**
         * 默认的string
         *
         * @return 字符串 java . lang . string
         */
        java.lang.String defString() default "";

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * {@link java.util.Deque}类型
     * 需要设置序列化和反序列化方法(序列化可使用object的)
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Deque {

        /**
         * 返回值类型
         *
         * @return the class
         */
        Class<?> type();

        /**
         * 默认值从该key关联的注解静态方法或常量中获取，若为静态方法，则最多仅能有一个参数，
         * 返回的对象必须是该类型{@link java.util.Deque}
         *
         * @return 关联的id int
         */
        int defValFromId() default 0;

        /**
         * 默认值是否为null，当为true时，那么其他默认值无效
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defNull() default false;

        /**
         * 默认值是否为空列表，
         * 优先级低于{@link #defNull()}
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defEmpty() default true;

        /**
         * 默认的string
         *
         * @return 字符串 java . lang . string
         */
        java.lang.String defString() default "";

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * SparseArray类型
     * 需要设置序列化和反序列化方法(序列化可使用object的)
     * 这是android库的类，若使用Gson库则需要手动添加序列化与反序列化适配器
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface SparseArray {

        /**
         * 返回值类型
         *
         * @return the class
         */
        Class<?> type();

        /**
         * 默认值从该key关联的注解静态方法或常量中获取，若为静态方法，则最多仅能有一个参数，
         * 返回的对象必须是该类型{@link java.util.Deque}
         *
         * @return 关联的id int
         */
        int defValFromId() default 0;

        /**
         * 默认值是否为null，当为true时，那么其他默认值无效
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defNull() default false;

        /**
         * 默认值是否为空列表，
         * 优先级低于{@link #defNull()}
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defEmpty() default true;

        /**
         * 默认的string
         *
         * @return 字符串 java . lang . string
         */
        java.lang.String defString() default "";

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }

    /**
     * {@link java.util.Map}类型
     * 需要设置序列化和反序列化方法(序列化可使用object的)
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.FIELD)
    public @interface Map {

        /**
         * 返回值类型
         *
         * @return the class
         */
        Class<?> keyType();

        /**
         * 返回值的泛型
         *
         * @return the class
         */
        Class<?> valType();

        /**
         * 默认值从该key关联的注解静态方法或常量中获取，若为静态方法，则最多仅能有一个参数，
         * 返回的对象必须是该类型{@link java.util.Map}
         *
         * @return 关联的id int
         */
        int defValFromId() default 0;

        /**
         * 默认值是否为null，当为true时，那么其他默认值无效
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defNull() default false;

        /**
         * 默认值是否为空列表，
         * 优先级低于{@link #defNull()}
         * {@link #defValFromId()}
         *
         * @return 是否为null boolean
         */
        boolean defEmpty() default true;

        /**
         * 默认的string
         *
         * @return 字符串 java . lang . string
         */
        java.lang.String defString() default "";

        /**
         * Prefix type class.
         *
         * @return the class
         */
        Class<?> prefixType() default void.class;

        /**
         * Suffix type class.
         *
         * @return the class
         */
        Class<?> suffixType() default void.class;
    }
}
