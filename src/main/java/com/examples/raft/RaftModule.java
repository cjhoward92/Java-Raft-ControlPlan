package com.examples.raft;

import com.examples.raft.config.RaftConfig;
import com.google.inject.AbstractModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RaftModule extends AbstractModule {

    private static final Logger logger = LogManager.getLogger(RaftModule.class);

    private final RaftConfig config;

    public RaftModule(final RaftConfig config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        logger.info("Beginning raft module config");

        bind(RaftConfig.class).toInstance(this.config);
    }
}
