package org.deepdive.concurrency.task_002;

public class UnsafeStatesFinal implements StatesInterface {

    private final String[] states = new String[] { "AK", "AL" };

    @Override
    public String[] getStates() {
        return states;
    }
}
