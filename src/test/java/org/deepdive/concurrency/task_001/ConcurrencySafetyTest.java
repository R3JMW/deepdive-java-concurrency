package org.deepdive.concurrency.task_001;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencySafetyTest {

    class UnsafeSequence {
        private int count = 0;

        int get() {
            return count++;
        }

        int getCount() {
            return count;
        }
    }

    class SafeSequence {
        private int count = 0;

        synchronized int get() {
            return count++;
        }

        int getCount() {
            return count;
        }
    }

    int THREAD_LIMIT = 1000000;

    @Test
    public void unsafe() throws InterruptedException {
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_LIMIT);
        ExecutorService threadPool = this.getThreadPool(2);
        for (int i = 0; i < THREAD_LIMIT; i++) {
            threadPool.execute(() -> {

                unsafeSequence.get();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        threadPool.shutdown();

        Assertions.assertEquals(THREAD_LIMIT, unsafeSequence.getCount());
    }

    @Test
    public void safe() throws InterruptedException {
        SafeSequence safeSequence = new SafeSequence();
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_LIMIT);
        ExecutorService threadPool = this.getThreadPool(2);
        for (int i = 0; i < THREAD_LIMIT; i++) {
            threadPool.execute(() -> {
                safeSequence.get();
                countDownLatch.countDown();
            });

        }
        countDownLatch.await();
        threadPool.shutdown();

        Assertions.assertEquals(THREAD_LIMIT, safeSequence.getCount());
    }

    private ExecutorService getThreadPool(int threads) {
        return Executors.newFixedThreadPool(threads);
    }
}
