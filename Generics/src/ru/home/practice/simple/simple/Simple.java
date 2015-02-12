package ru.home.practice.simple;

/**
 * Created by dima on 05.02.15.
 */
public class Simple {
    public static void main(String[] args) {
        Container<Product> c1 = new Container<>();
        c1.setItem(new Phone());
        c1.setItem(new Camera());
        Product p = c1.getItem();

//        Container<Phone> c2;
        Container<Camera> c3;
//        Container<String> c2 = new Container<>(); Compile Error

    }
}
