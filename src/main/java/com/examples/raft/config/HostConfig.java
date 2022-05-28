package com.examples.raft.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class HostConfig {

    private int port;
    private String id;
}
