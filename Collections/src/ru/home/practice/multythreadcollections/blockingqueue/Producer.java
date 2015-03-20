package ru.home.practice.multythreadcollections.blockingqueue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by darshinkin on 20.03.15.
 */
public class Producer implements Runnable {
    private SynchronousQueue<Integer> queue;

    public Producer(SynchronousQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < Example.CNT; i++) {
            long random_work_time = new Random().nextInt(1000);
            try {
                System.out.println("Producer waiting.");
                Thread.sleep(random_work_time);
                queue.put(i);
                System.out.println("Producer putted: "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
