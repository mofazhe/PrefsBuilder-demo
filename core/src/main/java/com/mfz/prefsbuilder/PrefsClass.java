package com.mfz.prefsbuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Prefs class.
 *
 * @author mz
 * @date 2020 /05/19/Tue
 * @time 11 :09
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface PrefsClass {
    /**
     * 最终的新类名是 统一前缀+这个值+统一后缀
     *
     * @return the string
     */
    String className() default "";

    /**
     * 若这个值不为空则优先使用该值，
     * 否则使用统一的包名
     *
     * @return the string
     */
    String pkgName() default "";

    /**
     * 这个值若为true则优先使用当前的类名作为新建的类名，
     * 否则取{@link PrefsClass#className()}的值
     * 该值为true时，最终的新类名为 统一前缀+当前类名+统一后缀
     *
     * @return the boolean
     */
    boolean currentClassName() default true;

    /**
     * 这个值若为true则优先使用当前包名，
     * 否则取{@link PrefsClass#pkgName()}的值
     *
     * @return the boolean
     */
    boolean currentPkg() default true;

    /**
     * 默认为统一classname转小写中间用下划线分隔
     *
     * @return the string
     */
    String fileName() default "";
}
