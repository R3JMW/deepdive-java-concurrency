package org.deepdive.concurrency.task_002;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class SafeStatesTest {

    static StatesFactory sf;

    @BeforeAll
    public static void initFactory() {
        sf = new StatesFactory();
    }

    @Test
    public void testUnsafeStates() throws InterruptedException {
        UnsafeStates unsafeStates = (UnsafeStates) sf.get(StatesFactory.StatesType.UNSAFE_STATES);

        CountDownLatch cdl = new CountDownLatch(1);
        new Thread(() -> {
            String[] states = unsafeStates.getStates();
            states[0] = "BC";
            cdl.countDown();
        }).start();
        cdl.await();

        Assertions.assertEquals(unsafeStates.getStates()[0], "BC");
    }

    @Test
    public void testUnsafeStatesFinal() throws InterruptedException {
        UnsafeStatesFinal unsafeStatesFinal = (UnsafeStatesFinal) sf.get(StatesFactory.StatesType.UNSAFE_STATES_FINAL);

        CountDownLatch cdl = new CountDownLatch(1);
        new Thread(() -> {
            String[] states = unsafeStatesFinal.getStates();
            states[0] = "BC";
            cdl.countDown();
        }).start();
        cdl.await();

        Assertions.assertEquals(unsafeStatesFinal.getStates()[0], "BC");
    }

    @Test
    public void testSafeStates() throws InterruptedException {
        SafeStatesCopy safeStates = (SafeStatesCopy) sf.get(StatesFactory.StatesType.SAFE_STATES_COPY);

        CountDownLatch cdl = new CountDownLatch(1);
        new Thread(() -> {
            String[] states = safeStates.getStates();
            states[0] = "BC";
            cdl.countDown();
        }).start();
        cdl.await();

        Assertions.assertNotEquals(safeStates.getStates()[0], "BC");
    }}
