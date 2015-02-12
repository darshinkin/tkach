package ru.home.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 05.02.15.
 */
public class RawTypes {
    public static void main(String[] args) {
        List rawList = new ArrayList();
        List<String> list = new ArrayList<>();

        rawList = list;
        rawList.add(8);

        String s = list.get(0);
    }
}
