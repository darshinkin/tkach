package ru.home.practice;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dima on 25.01.15.
 */
public class Account {
    private int balance;
    private Lock lock;
    private AtomicInteger failCounter;

    public Account(int initialBalance, int initialFailCounter) {
        this.balance = initialBalance;
        this.lock = new ReentrantLock(true);
        this.failCounter = new AtomicInteger(initialFailCounter);
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void deposit(int amaount) {
        balance += amaount;
    }

    public int getBalance() {
        return balance;
    }

    public Lock getLock() {
        return lock;
    }

    public void incFailedTransferCount() {
        failCounter.incrementAndGet();
    }

    public int getFailCounter() {
        return failCounter.get();
    }
}
