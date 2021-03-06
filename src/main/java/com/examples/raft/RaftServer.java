package com.examples.raft;

import com.examples.raft.cmd.RaftServerOptions;
import com.examples.raft.config.RaftConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RaftServer {

    private static final Logger logger = LogManager.getLogger(RaftServer.class);

    private Server server;
    private final Injector injector;

    public RaftServer(final Injector injector) {
        this.injector = injector;
    }

    public void start(final RaftConfig config) throws IOException {
        server = ServerBuilder.forPort(config.getHostConfig().getPort())
                .addService(new RaftImpl())
                .build()
                .start();

        logger.info("server boot complete on port {}", server.getPort());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.error("Shutting down");
            RaftServer.this.stop();
            logger.error("Shut down complete");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        var config = RaftServerOptions.parseCommandLine(args);

        Injector injector = Guice.createInjector(new RaftModule(config));

        final RaftServer raftServer = new RaftServer(injector);
        raftServer.start(config);
        raftServer.blockUntilShutdown();
    }

    static class RaftImpl extends RaftGrpc.RaftImplBase {

        private static final Logger logger = LogManager.getLogger(RaftImpl.class);

        @Override
        public void appendEntries(com.examples.raft.AppendRequest request,
                                  io.grpc.stub.StreamObserver<com.examples.raft.AppendResponse> responseObserver) {
            logger.info("Calling append entries with term {}", request.getTerm());
            responseObserver.onNext(AppendResponse.newBuilder().setSuccess(true).setTerm(1).build());
            responseObserver.onCompleted();
        }

        @Override
        public void requestVote(com.examples.raft.VoteRequest request,
                                io.grpc.stub.StreamObserver<com.examples.raft.VoteResponse> responseObserver) {
            logger.info("Calling vote with term {}", request.getTerm());
            responseObserver.onNext(VoteResponse.newBuilder().setVoteGranted(true).setTerm(1).build());
            responseObserver.onCompleted();
        }

        @Override
        public void installSnapshot(com.examples.raft.InstallSnapshotRequest request,
                                       io.grpc.stub.StreamObserver<com.examples.raft.InstallSnapshotResponse> responseObserver) {
            logger.info("Calling install snapshot with term {}", request.getTerm());
            responseObserver.onNext(InstallSnapshotResponse.newBuilder().setTerm(1).build());
            responseObserver.onCompleted();
        }
    }
}
