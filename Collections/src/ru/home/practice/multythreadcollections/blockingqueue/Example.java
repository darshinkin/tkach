package ru.home.practice.multythreadcollections.blockingqueue;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by darshinkin on 20.03.15.
 */
public class Example {
    public static final int CNT = 3;

    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        Runnable producer = new Producer(queue);
        Runnable cunsumer = new Cunsumer(queue);
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(cunsumer);
        t1.start();
        t2.start();
    }
}
