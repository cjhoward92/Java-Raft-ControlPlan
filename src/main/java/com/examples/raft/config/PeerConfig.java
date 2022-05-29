package com.examples.raft.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.cli.CommandLine;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.examples.raft.cmd.CommandLineConstants.PEERS_OPTION;

@Builder
@Data
@AllArgsConstructor
public class PeerConfig {

    private Map<String, Peer> peers;

    public static PeerConfig fromCommandLine(CommandLine commandLine) {
        var config = new PeerConfig(new HashMap<>());

        var peerList = commandLine.getOptionValue(PEERS_OPTION);
        config.peers = Arrays.stream(peerList.split(",")).map((peerString) -> {
            var parts = peerString.split("\\|");

            if (parts.length != 2) {
                throw new IllegalStateException(
                        String.format("Each peer in the peer list must be in the <uri>|<id> format! Invalid peer: %s",
                        peerString));
            }
            // TODO: validate URI

            return Peer.builder().uri(parts[0]).id(parts[1]).build();
        }).collect(Collectors.toMap(Peer::getId, p -> p));

        return config;
    }
}
