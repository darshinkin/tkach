package ru.home.practice.multythreadcollections;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dima on 31.01.15.
 */
public class TestThread implements Callable<Long> {
    private final CountDownLatch latch;
    private final Integer start;
    private final Integer finish;
    private final List<Integer> list;

    public TestThread(CountDownLatch latch, Integer start, Integer finish, List<Integer> list) {
        this.latch = latch;
        this.start = start;
        this.finish = finish;
        this.list = list;
    }

    @Override
    public Long call() throws Exception {
        latch.await();
        long startTime = System.nanoTime();
        for (int i = start; i < finish; i++) {
            list.get(i);
        }
        long finishTime = System.nanoTime();
        return finishTime - startTime;
    }
}
