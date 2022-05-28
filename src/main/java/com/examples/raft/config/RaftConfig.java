package com.examples.raft.config;

import com.examples.raft.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class RaftConfig {

    private PeerConfig peerConfig;
    private HostConfig hostConfig;
    private State state;
}
