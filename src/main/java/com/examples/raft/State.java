package com.examples.raft;

import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class State {

    private static final Logger logger = LogManager.getLogger(State.class);

    // Persistent state
    private long currentTerm;
    private String votedFor;
    private List<String> log;

    // Volatile state
    private long commitIndex = 0;
    private long lastApplied = 0;

    // Volatile leader state
    private Map<String, Long> nextIndex = new HashMap<>();
    private Map<String, Long> matchIndex = new HashMap<>();

    public State(long currentTerm, String votedFor, List<String> log, List<String> serverIds) {
        logger.info(
                "Initializing state at term {}, voted for {}, with {} log entries and {} servers",
                currentTerm,
                votedFor,
                log.size(),
                serverIds.size());
        this.currentTerm = currentTerm;
        this.votedFor = votedFor;
        this.log = new ArrayList<>(log);

        for (String id : serverIds) {
            this.nextIndex.put(id, (long)this.log.size());
            this.matchIndex.put(id, 0L);
        }
    }
}
