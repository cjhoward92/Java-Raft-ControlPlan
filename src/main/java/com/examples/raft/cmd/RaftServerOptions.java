package com.examples.raft.cmd;

import com.examples.raft.CriticalException;
import com.examples.raft.config.HostConfig;
import com.examples.raft.config.PeerConfig;
import com.examples.raft.config.RaftConfig;
import com.google.common.base.Preconditions;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import static com.examples.raft.cmd.CommandLineConstants.PORT_OPTION;
import static com.examples.raft.cmd.CommandLineConstants.PEERS_OPTION;
import static com.examples.raft.cmd.CommandLineConstants.ID_OPTION;


public class RaftServerOptions {

    public static Options getOptions() {
        var options = new Options();
        options.addOption("p", PORT_OPTION, true, "What port should the server run on?");
        options.addOption("i", ID_OPTION, true, "What is the id of this server?");
        options.addOption("p", PEERS_OPTION, true, "Comma separated list of peer servers in the format <uri>|<id>");
        return options;
    }

    public static RaftConfig parseCommandLine(final String[] args) {
        Preconditions.checkArgument(args != null, "args are required");
        Preconditions.checkArgument(args.length > 0, "args are required");

        var parser = new DefaultParser();
        try {
            var commandLine = parser.parse(getOptions(), args);

            Preconditions.checkArgument(commandLine.hasOption(PORT_OPTION), "the port option is required");
            Preconditions.checkArgument(commandLine.hasOption(ID_OPTION), "the id option is required");
            Preconditions.checkArgument(commandLine.hasOption(PEERS_OPTION), "the peers option is required");

            return RaftConfig.builder()
                    .hostConfig(HostConfig.fromCommandLine(commandLine))
                    .peerConfig(PeerConfig.fromCommandLine(commandLine))
                    .build();
        } catch (ParseException e) {
            throw new CriticalException(e);
        }
    }
}
