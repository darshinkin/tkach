package ru.home.practice;

import ru.home.exceptions.InsufficientFundsException;

import javax.naming.InsufficientResourcesException;
import java.util.concurrent.TimeUnit;

/**
 * Created by dima on 25.01.15.
 */
public class Operations {
    final static long WAIT_SEC = 1;
    final static int FAIL_COUNTER = 0;
    public static void main(String[] args) throws InsufficientFundsException {
        final Account a = new Account(1000, FAIL_COUNTER);
        final Account b = new Account(2000, FAIL_COUNTER);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    transfer(a, b, 500);
                } catch (InsufficientFundsException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        transfer(b, a, 300);
    }

    static void transfer(Account acc1, Account acc2, int amount) throws InsufficientFundsException {
        if (acc1.getBalance() < amount) {
            throw new InsufficientFundsException();
        }

        synchronized (acc1) {
            System.out.printf("Get the lock on acc1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (acc2) {
                System.out.printf("Get the lock on acc1");
                acc1.withdraw(amount);
                acc2.deposit(amount);
            }
        }

        System.out.printf("Transfer successful!");
    }

    static void transferWithLock(Account acc1, Account acc2, int amount) throws InterruptedException {
        if (acc1.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
            try {
                if (acc2.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                  try {
                      System.out.printf("Get the lock on acc1");
                      acc1.withdraw(amount);
                      acc2.deposit(amount);
                  } finally {
                      acc2.getLock().unlock();
                  }
                } else {
                    acc2.incFailedTransferCount();
                }
            } finally {
                acc1.getLock().unlock();
            }
        } else {
            acc1.incFailedTransferCount();
        }
    }
}
