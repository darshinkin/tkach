package ru.home.practice;

import ru.home.exceptions.InsufficientFundsException;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dima on 26.01.15.
 */
public class Transfer implements Callable<Boolean> {
    static int idTransfer = 1;
    final static long WAIT_SEC = 3;
    Account acc1;
    Account acc2;
    int amount;
    CountDownLatch startLatch;

    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
            new ThreadLocal<Integer>() {
                @Override protected Integer initialValue() {
                    return nextId.getAndIncrement();
                }
            };

    // Returns the current thread's unique ID, assigning it if necessary
    public static int get() {
        return threadId.get();
    }

    public Transfer(Account accountFrom, Account accountTo, int amount, CountDownLatch startLatch) {
        this.amount = amount;
        this.acc1 = accountFrom;
        this.acc2 = accountTo;
        this.startLatch = startLatch;
    }

    @Override
    public Boolean call() throws Exception {
        String nameThread = Thread.currentThread().getName();
        System.out.println(threadId + ": -------------TRANSFER----------------");
        System.out.println("Waiting to start");
        startLatch.await();
        System.out.println("Started");


        if (acc1.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
            System.out.println(threadId + ": Get the lock on acc1");
            try {
                System.out.println(threadId + ": acc's balance is " + acc1.getBalance());
                if (acc1.getBalance() < amount) {
                    System.out.println("Insufficient funds!!!");
                    throw new InsufficientFundsException();
                }
                if (acc2.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                    System.out.println(threadId + ": Get the lock on acc2");
                    try {
                        acc1.withdraw(amount);
                        acc2.deposit(amount);

                        int timeout = new Random(1000).nextInt(5000);
//                        System.out.println(threadId + ": Timeout after transfer is " + timeout);
                        Thread.sleep(timeout);
                        return true;
                    } finally {
                        acc2.getLock().unlock();
                        System.out.println(threadId + ": Lock on acc1 has unlocked");
                    }
                } else {
                    acc2.incFailedTransferCount();
                    return false;
                }
            } finally {
                acc1.getLock().unlock();
                System.out.println(threadId + ": Lock on acc1 has unlocked");
            }
        } else {
            acc1.incFailedTransferCount();
            return false;
        }
    }
}
