package ru.home.practice.simple;

/**
 * Created by dima on 05.02.15.
 */
public class Camera extends Product implements Comparable<Camera> {
    Integer pixel;

    @Override
    public int compareTo(Camera o) {
        return 0;
    }
}
