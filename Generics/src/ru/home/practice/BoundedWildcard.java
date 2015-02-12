package ru.home.practice;

import ru.home.practice.simple.Camera;
import ru.home.practice.simple.Product;

import java.util.List;

/**
 * Created by dima on 05.02.15.
 */
public class BoundedWildcard {
    void copy (List<? extends Product> src, List<? super Product> dest) {
        for (Product p : src) {
            dest.add(p);
        }
    }
}
