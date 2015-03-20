package ru.home.practice.multythreadcollections.blockingqueue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by darshinkin on 20.03.15.
 */
public class Cunsumer implements Runnable {
    private SynchronousQueue<Integer> queue;

    public Cunsumer(SynchronousQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int j = 0; j < Example.CNT; j++) {
                int i = queue.take();
                System.out.println("Cunsumer taked: "+j);
                long random_work_time = new Random().nextInt(100);
                Thread.sleep(random_work_time);
//                System.out.println("Cunsumer is wainting");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
