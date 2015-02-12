package ru.home.practice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dima on 27.01.15.
 */
public class SimpleLinkedHashMap {
    public static void main(String[] args) {
        Map<Integer, String> m = new LinkedHashMap<Integer, String>(5, 1, true);
        m.put(5, "a");
        m.put(4, "b");
        m.put(3, "c");
        m.put(2, "e");
        m.put(1, "d");
        m.get(3);
        m.get(5);
        m.get(3);
        m.get(1);
        System.out.println(m);
    }
}
