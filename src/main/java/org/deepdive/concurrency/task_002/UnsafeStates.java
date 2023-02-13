package org.deepdive.concurrency.task_002;

public class UnsafeStates implements StatesInterface {

    private String[] states = new String[] { "AK", "AL" };

    @Override
    public String[] getStates() {
        return states;
    }
}
