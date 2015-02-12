package ru.home.practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by dima on 05.02.15.
 */
public class SomeType<T> {

    public <E> void test (Collection<E> collection) {
        for (E e : collection) {
            System.out.println(e);
        }
    }

    public void test (List<Integer> integerList) {
        for (Integer integer : integerList) {
            System.out.println(integer);
        }
    }

    public static void main(String[] args) {
        SomeType<?> someType = new SomeType();
        List<String> list = Arrays.asList("value");
        someType.test(list);
    }
}
