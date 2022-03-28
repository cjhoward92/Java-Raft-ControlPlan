package com.examples.raft;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RaftServer {

    private static final Logger logger = LogManager.getLogger(RaftServer.class);

    private Server server;

    public void start(int port) throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new RaftImpl())
                .build()
                .start();

        logger.info("server boot complete on port {}", port);
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
        int port = 50001;

        if (args.length > 0) {
            logger.info("Setting port to {}", args[0]);
            port = Integer.valueOf(args[0]);
        }

        final RaftServer raftServer = new RaftServer();
        raftServer.start(port);
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
