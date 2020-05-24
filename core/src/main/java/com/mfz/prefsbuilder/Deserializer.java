package com.mfz.prefsbuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 反序列化注解
 *
 * @author mz
 * @date 2020 /05/15/Fri
 * @time 18 :17
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Deserializer {

    /**
     * The interface Object.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface Object {
    }

    /**
     * The interface List.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface List {
    }

    /**
     * The interface Set.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface Set {
    }

    /**
     * The interface Queue.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface Queue {
    }

    /**
     * The interface Deque.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface Deque {
    }

    /**
     * The interface Sparse array.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface SparseArray {
    }

    /**
     * The interface Map.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.METHOD)
    @interface Map {
    }
}
