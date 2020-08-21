package com.mfz.demo.utils;

import android.util.SparseArray;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import com.mfz.prefsbuilder.DefaultValue;
import com.mfz.prefsbuilder.PrefsClass;
import com.mfz.prefsbuilder.PrefsVal;

/**
 * @author mz
 * @date 2020/05/14/Thu
 * @time 10:17
 */
@PrefsClass(className = "Settings", currentClassName = false)
public class PrefsConst {
    @PrefsClass()
    public static class User {
        @PrefsVal.Int()
        public static final String TEST_INT = "null";
        @PrefsVal.String(defNull = true)
        public static final String TEST_STRING = "b";
        @PrefsVal.String(defVal = "111")
        public static final String TEST_STRING_NOT = "b";
        @PrefsVal.Byte()
        public static final String TEST_BYTE = "c";
    }

    @PrefsVal.Int(defVal = 122, defValFromId = 123)
    public static final String TEST_INT = "a";
    @PrefsVal.Float(defVal = 1.233300f, defValFromId = 133)
    public static final String TEST_FLOAT = "float";
    @PrefsVal.Bool(defVal = true, defValFromId = 144)
    public static final String TEST_BOOL = "a";
    @PrefsVal.Byte(defVal = 12, defValFromId = 155)
    public static final String TEST_BYTE = "a";
    @PrefsVal.Double(defVal = 102.2435235, defValFromId = 166)
    public static final String TEST_DOUBLE = "a";
    @PrefsVal.Char(defVal = 's', defValFromId = 177, prefixType = byte.class)
    public static final String TEST_CHAR = "a";
    @PrefsVal.Short(defVal = 234, defValFromId = 188)
    public static final String TEST_SHORT = "a";
    @PrefsVal.String(defValFromId = 111, prefixType = float.class, suffixType = double.class)
    public static final String TEST_STRING = "b";
    @PrefsVal.String(defVal = "[\"foo\", {\"bar\": [\"baz\", null, 1.0, 2]}]")
    public static final String TEST_STRING_JSON = "b";
    @PrefsVal.String(defNull = true, prefixType = String.class, suffixType = int.class, codecId = 1)
    public static final String TEST_STRING_BASE64 = "b";
    @PrefsVal.Object(type = Test.class, defValFromId = 1, defNull = true)
    public static final String TEST_OBJECT = "c";
    @PrefsVal.List(type = Test.class, defEmpty = false, defValFromId = 2, defString = "[]")
    public static final String TEST_LIST = "d";
    @PrefsVal.List(type = Test.class)
    public static final String TEST_LIST_EMPTY = "d";
    @PrefsVal.Set(type = Test.class, defEmpty = false, defValFromId = 3)
    public static final String TEST_SET = "e";
    @PrefsVal.Queue(type = String.class, defEmpty = false, defValFromId = 4, suffixType = String.class)
    public static final String TEST_QUEUE = "f";
    @PrefsVal.Deque(type = Test.class, defEmpty = false, defValFromId = 5, prefixType = int.class)
    public static final String TEST_DEQUE = "g";
    @PrefsVal.SparseArray(type = String.class, defEmpty = false, defValFromId = 7, prefixType = int.class)
    public static final String TEST_SPARSE_ARRAY = "g";
    @PrefsVal.Map(keyType = String.class, defEmpty = false, valType = Test.class, defValFromId = 6)
    public static final String TEST_MAP = "h";

    @DefaultValue(id = 123)
    public static int iii = 22;
    @DefaultValue(id = 7)
    public static final SparseArray<String> sSparseArray = new SparseArray<>();
    @DefaultValue(id = 111)
    public static String sss = "new Test()";
    public static Test sTest = new Test();
    @DefaultValue(id = 3)
    public static Set<Test> sList = Collections.emptySet();
    @DefaultValue(id = 4)
    public static final Queue<String> MY_QUEUE = new ArrayBlockingQueue<>(1);
    @DefaultValue(id = 5)
    public static final Deque<Test> MyQueue = new LinkedList<>();
    @DefaultValue(id = 6)
    public static final Map<String, Test> MyMap = new HashMap<>();

    @DefaultValue(id = 1)
    public static Test getTest() {
        return new Test();
    }

    @DefaultValue(id = 2)
    public static List<Test> getList() {
        return Collections.emptyList();
    }

    public static String getNewString() {
        return "";
    }

    public static class Test {

    }
}
