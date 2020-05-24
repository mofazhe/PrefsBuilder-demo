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
     * 最终的名字是 统一前缀+这个值+统一后缀
     *
     * @return the string
     */
    String className();

    /**
     * 若这个值不为空则优先使用该值，
     * 否则使用统一的包名
     *
     * @return the string
     */
    String pkgName() default "";

    /**
     * 这个值若为true则优先使用当前包名，
     * 否则取{@link PrefsClass#pkgName()}的值
     *
     * @return the boolean
     */
    boolean currentPkg() default false;

    /**
     * 默认为统一classname转小写中间用下划线分隔
     *
     * @return the string
     */
    String fileName() default "";
}
