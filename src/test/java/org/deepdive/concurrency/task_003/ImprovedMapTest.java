package org.deepdive.concurrency.task_003;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImprovedMapTest {

    int THREAD_LIMIT = 10000;

    @Test
    public void testImprovedMapThreadSafe() throws InterruptedException {
        ImprovedMap<Integer, Integer> improvedMap = new ImprovedMap<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_LIMIT);
        for(int i = 0; i < THREAD_LIMIT; i++) {
            final int key = i;
            threadPool.execute(() -> {
                improvedMap.put(key, key);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        threadPool.shutdown();
        Assertions.assertEquals(improvedMap.size(), THREAD_LIMIT);
    }

    @Test
    public void testHashMapThreadUnsafe() throws InterruptedException {
        HashMap<Integer, Integer> improvedMap = new HashMap<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_LIMIT);
        for(int i = 0; i < THREAD_LIMIT; i++) {
            final int key = i;
            threadPool.execute(() -> {
                improvedMap.put(key, key);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        threadPool.shutdown();
        Assertions.assertEquals(improvedMap.size(), THREAD_LIMIT);
    }
}
