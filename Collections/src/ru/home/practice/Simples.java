package ru.home.practice;

import java.util.*;

/**
 * Created by dima on 27.01.15.
 */
public class Simples {
    static int testInt = 6;
    public static void main(String[] args) {
        //simpleTreeSet();
        simpleSortedSet();
    }

    private static void simpleSortedSet() {
        NavigableSet<Integer> set = new TreeSet<Integer>();
        for (int i=1; i<=10; i++) {
            set.add(i);
        }
//        Integer res = getNextElement(testInt, set);
        System.out.println("Next element of "+testInt+" is "+set.higher(testInt));

//        Set<Integer> resSet = getPrevElems(testInt, set);
        System.out.println("Set of previous elements of "+testInt+" is "+set.headSet(testInt));
    }

    private static Set<Integer> getPrevElems(int testInt, SortedSet<Integer> set) {
        Set<Integer> res = new TreeSet<Integer>();
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            Integer next = it.next();
            if (next <= testInt) {
                res.add(next);
            }
        }
        return res;
    }

    private static Integer getNextElement(int i, SortedSet<Integer> set) {
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            Integer next = it.next();
            if (next > i) {
                return next;
            }
        }
        return 0;
    }

    private static void simpleTreeSet() {
        Set<Integer> set = new TreeSet<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });
        set.add(1);
        set.add(2);
        set.add(1);
        System.out.println(set);
    }
}
