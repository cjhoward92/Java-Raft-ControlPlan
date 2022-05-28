package com.examples.raft.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
@AllArgsConstructor
public class PeerConfig {

    private Map<String, Peer> peers;
}
