package org.deepdive.concurrency.task_004;

import org.deepdive.concurrency.task_003.ImprovedMap;
import org.openjdk.jmh.annotations.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@State(value = Scope.Benchmark)
@Threads(2)
@Warmup(time = 1, iterations = 1)
public class TestWithJMH    {

    private Map<Integer, Object> syncMap = Collections.synchronizedMap(new HashMap<>());
    private Map<Integer, Object> concMap = new ConcurrentHashMap<>();
    private Map<Integer, Object> improvedMap = new ImprovedMap<>();

    private static final int POOL_SIZE = 2;
    private static final int COUNT =10000;

    @Benchmark
    public void syncMapTest() throws InterruptedException {
        ExecutorService pool = this.getThreadPool(POOL_SIZE);
        CountDownLatch countDownLatch = new CountDownLatch(POOL_SIZE);
        pool.execute(() -> {
            for (int i = 0; i < COUNT; i++) {
                int randomInt = ThreadLocalRandom.current().nextInt();
                syncMap.put(randomInt, randomInt);
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();
        pool.shutdown();
    }

    @Benchmark
    public void concMapTest() throws InterruptedException {
        ExecutorService pool = this.getThreadPool(POOL_SIZE);
        CountDownLatch countDownLatch = new CountDownLatch(POOL_SIZE);
        pool.execute(() -> {
            for (int i = 0; i < COUNT; i++) {
                int randomInt = ThreadLocalRandom.current().nextInt();
                concMap.put(randomInt, randomInt);
            }
        });

        countDownLatch.await();
        pool.shutdown();
    }

    @Benchmark
    public void improvedMapTest() throws InterruptedException {
        ExecutorService pool = this.getThreadPool(POOL_SIZE);
        CountDownLatch countDownLatch = new CountDownLatch(POOL_SIZE);
        pool.execute(() -> {
            for(int i = 0; i < COUNT; i++) {
                int randomInt = ThreadLocalRandom.current().nextInt();
                improvedMap.put(randomInt,randomInt);
            }
        });

        countDownLatch.await();
        pool.shutdown();
    }

    private ExecutorService getThreadPool(int threads) {
        return Executors.newFixedThreadPool(threads);
    }
}
