package com.mfz.prefsbuilder.annotationprocessor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mfz.prefsbuilder.Deserializer;
import com.mfz.prefsbuilder.PrefsVal;
import com.mfz.prefsbuilder.Serializer;

/**
 * @author mz
 * @date 2020/05/14/Thu
 * @time 15:20
 */
public class AnnotationList {
    public static Set<Class<? extends Annotation>> getPrefsVal() {
        Set<Class<? extends Annotation>> set = new HashSet<>(50);
        set.add(PrefsVal.Int.class);
        set.add(PrefsVal.Float.class);
        set.add(PrefsVal.Bool.class);
        set.add(PrefsVal.Byte.class);
        set.add(PrefsVal.Double.class);
        set.add(PrefsVal.Char.class);
        set.add(PrefsVal.Long.class);
        set.add(PrefsVal.Short.class);
        set.add(PrefsVal.String.class);
        set.add(PrefsVal.Object.class);
        set.add(PrefsVal.List.class);
        set.add(PrefsVal.Set.class);
        set.add(PrefsVal.Queue.class);
        set.add(PrefsVal.Deque.class);
        set.add(PrefsVal.SparseArray.class);
        set.add(PrefsVal.Map.class);
        return set;
    }

    public static Map<Class<? extends Annotation>, Integer> getRuleMethod() {
        Map<Class<? extends Annotation>, Integer> map = new HashMap<>(100);
        map.put(Serializer.Object.class, Const.Key.Serializer.OBJ);
        map.put(Serializer.List.class, Const.Key.Serializer.LIST);
        map.put(Serializer.Set.class, Const.Key.Serializer.SET);
        map.put(Serializer.Queue.class, Const.Key.Serializer.QUEUE);
        map.put(Serializer.Deque.class, Const.Key.Serializer.DEQUE);
        map.put(Serializer.SparseArray.class, Const.Key.Serializer.SPARSE_ARRAY);
        map.put(Serializer.Map.class, Const.Key.Serializer.MAP);

        map.put(Deserializer.Object.class, Const.Key.Deserializer.OBJ);
        map.put(Deserializer.List.class, Const.Key.Deserializer.LIST);
        map.put(Deserializer.Set.class, Const.Key.Deserializer.SET);
        map.put(Deserializer.Queue.class, Const.Key.Deserializer.QUEUE);
        map.put(Deserializer.Deque.class, Const.Key.Deserializer.DEQUE);
        map.put(Deserializer.SparseArray.class, Const.Key.Deserializer.SPARSE_ARRAY);
        map.put(Deserializer.Map.class, Const.Key.Deserializer.MAP);
        return map;
    }

    public static Map<Class<? extends Annotation>, List<Integer>> getDeserializerByVal() {
        List<Integer> list;
        Map<Class<? extends Annotation>, List<Integer>> map = new HashMap<>(20);

        list = new ArrayList<>(5);
        list.add(Const.Key.Deserializer.OBJ);
        map.put(PrefsVal.Object.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Deserializer.LIST);
        list.add(Const.Key.Deserializer.OBJ);
        map.put(PrefsVal.List.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Deserializer.SET);
        list.add(Const.Key.Deserializer.OBJ);
        map.put(PrefsVal.Set.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Deserializer.QUEUE);
        list.add(Const.Key.Deserializer.OBJ);
        map.put(PrefsVal.Queue.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Deserializer.DEQUE);
        list.add(Const.Key.Deserializer.OBJ);
        map.put(PrefsVal.Deque.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Deserializer.SPARSE_ARRAY);
        list.add(Const.Key.Deserializer.OBJ);
        map.put(PrefsVal.SparseArray.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Deserializer.MAP);
        list.add(Const.Key.Deserializer.OBJ);
        map.put(PrefsVal.Map.class, list);

        return map;
    }

    public static Map<Class<? extends Annotation>, List<Integer>> getSerializerByVal() {
        List<Integer> list;
        Map<Class<? extends Annotation>, List<Integer>> map = new HashMap<>(20);

        list = new ArrayList<>(5);
        list.add(Const.Key.Serializer.OBJ);
        map.put(PrefsVal.Object.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Serializer.LIST);
        list.add(Const.Key.Serializer.OBJ);
        map.put(PrefsVal.List.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Serializer.SET);
        list.add(Const.Key.Serializer.OBJ);
        map.put(PrefsVal.Set.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Serializer.QUEUE);
        list.add(Const.Key.Serializer.OBJ);
        map.put(PrefsVal.Queue.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Serializer.DEQUE);
        list.add(Const.Key.Serializer.OBJ);
        map.put(PrefsVal.Deque.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Serializer.SPARSE_ARRAY);
        list.add(Const.Key.Serializer.OBJ);
        map.put(PrefsVal.SparseArray.class, list);

        list = new ArrayList<>(5);
        list.add(Const.Key.Serializer.MAP);
        list.add(Const.Key.Serializer.OBJ);
        map.put(PrefsVal.Map.class, list);

        return map;
    }
}
