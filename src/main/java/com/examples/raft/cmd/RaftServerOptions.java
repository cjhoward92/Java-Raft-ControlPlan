package com.examples.raft.cmd;

import com.examples.raft.State;
import com.examples.raft.config.RaftConfig;
import org.apache.commons.cli.Options;

public class RaftServerOptions {

    public static Options getOptions() {
        Options options = new Options();
        options.addOption("p", "port", true, "What port should the server run on?");
        options.addOption("i", "id", true, "What is the id of this server?");
        options.addOption("p", "peers", true, "Comma separated list of peer servers in the format <uri>|<id>");
        return options;
    }

    public static RaftConfig parseCommandLine() {
        return null;
    }
}
