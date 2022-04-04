package com.examples.raft;

public interface StatePersistence {
    boolean save(final String key, final State state);
    State load(final String key);
}
