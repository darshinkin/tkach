package ru.home.practice;

import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by dima on 28.01.15.
 */
public class SimpleWeakHashMap {
    public static void main(String[] args) {
        Map<Date, String> map = new WeakHashMap<Date, String>();
        Date date = new Date();
        map.put(date, "information");

        date = null;
        System.gc();

        for (int i = 0; i <= 10000; i++) {
            if (map.isEmpty()) {
                System.out.println(i+": Empty!");
                break;
            } else if (i == 10000) {
                System.out.println("GC was not worked!");
            }
        }
    }
}
