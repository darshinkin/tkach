package ru.home.practice;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dima on 26.01.15.
 */
public class Operations2 {
    final static int FAIL_COUNTER = 0;
    final static long TIMEOUT = 30;
    final static Account acc1 = new Account(1000, FAIL_COUNTER);
    final static Account acc2 = new Account(2000, FAIL_COUNTER);

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(3);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(acc1.getFailCounter());
            }
        }, 2, 10, TimeUnit.SECONDS);

        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            Transfer transfer = new Transfer(acc1, acc2, rnd.nextInt(400), startLatch);
            service.submit(transfer);
        }
        startLatch.countDown();

        service.shutdown();
        service.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
    }
}
