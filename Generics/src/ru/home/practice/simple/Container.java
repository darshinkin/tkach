package ru.home.practice.simple;

/**
 * Created by dima on 05.02.15.
 */
public class Container <T extends Product & Comparable<T>> {
    private T item;

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
