package com.mfz.prefsbuilder.annotationprocessor;

/**
 * @author mz
 * @date 2020/05/13/Wed
 * @time 18:27
 */
public interface Const {

    interface OptionArg {
        String PKG = "prefsBuilderPkg";
        String CLASS_PREFIX = "prefsBuilderClassPrefix";
        String CLASS_SUFFIX = "prefsBuilderClassSuffix";
    }

    interface Default {
        String PKG = "com.mfz.prefs";
        String CLASS_PREFIX = "";
        String CLASS_SUFFIX = "";
    }

    interface Key {
        interface Serializer {
            int OBJ = 1;
            int LIST = 2;
            int SET = 3;
            int QUEUE = 4;
            int DEQUE = 5;
            int SPARSE_ARRAY = 6;
            int MAP = 7;
        }

        interface Deserializer {
            int OBJ = 51;
            int LIST = 52;
            int SET = 53;
            int QUEUE = 54;
            int DEQUE = 55;
            int SPARSE_ARRAY = 56;
            int MAP = 57;
        }

    }

}
