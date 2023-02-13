package org.deepdive.concurrency.task_002;

import java.util.Arrays;

public class SafeStatesCopy implements StatesInterface {

    private String[] states = new String[] { "AK", "AL" };

    @Override
    public String[] getStates() {
        return Arrays.copyOf(states, states.length);
    }
}
