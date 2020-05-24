package com.mfz.prefsbuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 序列化
 *
 * @author mz
 * @date 2020 /05/15/Fri
 * @time 18 :17
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Serializer {

    /**
     * object的序列化方法
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface Object {
    }

    /**
     * list对象优先使用，可以不单独定义，不定义则使用
     * {@link Serializer.Object}
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface List {
    }

    /**
     * set对象优先使用，可以不单独定义，不定义则使用
     * {@link Serializer.Object}
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface Set {
    }

    /**
     * Queue对象优先使用，可以不单独定义，不定义则使用
     * {@link Serializer.Object}
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface Queue {
    }

    /**
     * Queue对象优先使用，可以不单独定义，不定义则使用
     * {@link Serializer.Object}
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface Deque {
    }

    /**
     * Queue对象优先使用，可以不单独定义，不定义则使用
     * {@link Serializer.Object}
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface SparseArray {
    }

    /**
     * map对象优先使用，可以不单独定义，不定义则使用
     * {@link Serializer.Object}
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface Map {
    }
}
