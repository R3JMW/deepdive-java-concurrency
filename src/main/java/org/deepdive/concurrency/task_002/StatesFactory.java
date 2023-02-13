package org.deepdive.concurrency.task_002;

public class StatesFactory {

    public enum StatesType {
        UNSAFE_STATES,
        UNSAFE_STATES_FINAL,
        SAFE_STATES_COPY
    }

    StatesInterface unsafe;
    StatesInterface unsafeFinal;
    StatesInterface safeCopy;

    public StatesFactory() {
        unsafe = new UnsafeStates();
        unsafeFinal = new UnsafeStatesFinal();
        safeCopy = new SafeStatesCopy();
    }

    public StatesInterface get(StatesType type) {
        switch (type) {
            case UNSAFE_STATES:
                return unsafe;
            case UNSAFE_STATES_FINAL:
                return unsafeFinal;
            case SAFE_STATES_COPY:
                return safeCopy;
            default:
                throw new IllegalArgumentException();
        }
    }
}
