package com.examples.raft.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.cli.CommandLine;

import static com.examples.raft.cmd.CommandLineConstants.ID_OPTION;
import static com.examples.raft.cmd.CommandLineConstants.PORT_OPTION;

@Builder
@AllArgsConstructor
@Data
public class HostConfig {

    private int port;
    private String id;

    public static HostConfig fromCommandLine(CommandLine commandLine) {
        return HostConfig.builder()
                .id(commandLine.getOptionValue(ID_OPTION))
                .port(Integer.parseInt(commandLine.getOptionValue(PORT_OPTION)))
                .build();
    }
}
