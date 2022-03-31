package com.examples.raft;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

@Singleton
public class StateProvider implements Provider<State> {

    private State prebuiltState = null;

    // TODO(cjhoward92): Get persistent state from somewhere
    public State get() {
        if (prebuiltState != null) {
            return prebuiltState;
        }

        prebuiltState = new State(
                0,
                "",
                new ArrayList<>(),
                new ArrayList<>());
        return prebuiltState;
    }

}
