package ru.home.practice.multythreadcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by dima on 31.01.15.
 */
public class TestPerformance {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> threadSafeList = new CopyOnWriteArrayList<Integer>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<Integer>());

        fillList(threadSafeList, synchronizedList);

        System.out.println("List synchronized:");
        checkList(synchronizedList);

        System.out.println("CopyOnWriteArrayList:");
        checkList(threadSafeList);
    }

    private static void checkList(List<Integer> list) throws ExecutionException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        ExecutorService ex = Executors.newFixedThreadPool(2);
        Future<Long> future1 = ex.submit(new TestThread(latch, 1, 50, list));
        Future<Long> future2 = ex.submit(new TestThread(latch, 51, 100, list));;
        latch.countDown();

        System.out.println("Thread 1: " + future1.get()/100);
        System.out.println("Thread 2: " + future2.get()/100);
    }

    private static void fillList(List<Integer> threadSafeList, List<Integer> synchronizedList) {
        for (int i = 1; i <= 100; i++) {
            threadSafeList.add(i);
            synchronizedList.add(i);
        }
    }
}
