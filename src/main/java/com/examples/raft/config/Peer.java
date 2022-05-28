package com.examples.raft.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Peer {

    private String id;
    private String uri;
}
