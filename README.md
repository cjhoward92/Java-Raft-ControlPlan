# Raft w/ Control Plane

This is a small project that will implement [Raft](https://raft.github.io/raft.pdf) as found
in the canonical white paper. It will also test some ideas for Control Theory where
we will develop a control plane to manage the Raft host configuration (e.g. add
another host to the Raft consensus group).

The project is written in Java, using Gradle as the build toolchain. The inter-service
calls will be facilitated with [gRPC](https://grpc.io/docs/languages/java/basics/).

## Running

At the time of writing, I am using `gradle v7.4.1`. The gradle config files (e.g. gradlew)
have been committed to the repo, for better or for worse, so in theory you can just run the
`./gradlew installDist` task to get everything built. From there, simple use
`./gradlew run` to run the project.

This assumes a lot and given my limited gradle knowledge might not be accurate.
Beware those who set up this repo after me.