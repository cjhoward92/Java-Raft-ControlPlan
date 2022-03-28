# Raft w/ Control Plane

This is a small project that will implement [Raft](https://raft.github.io/raft.pdf) as found
in the canonical white paper. It will also test some ideas for Control Theory where
we will develop a control plane to manage the Raft host configuration (e.g. add
another host to the Raft consensus group).

The project is written in Java, using Gradle as the build toolchain. The inter-service
calls will be facilitated with [gRPC](https://grpc.io/docs/languages/java/basics/).