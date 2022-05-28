package com.examples.raft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
@Builder
public class State {

    // Persistent state
    private String id;
    private long currentTerm;
    private String votedFor;
    private List<String> log;

    // Volatile state
    private long commitIndex;
    private long lastApplied;

    // Volatile leader state
    private Map<String, Long> nextIndex;
    private Map<String, Long> matchIndex;

    public static State init() {
        return State.builder()
                .build();
    }
}
