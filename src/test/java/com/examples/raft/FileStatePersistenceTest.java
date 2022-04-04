package com.examples.raft;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileStatePersistenceTest {

    private static final String testKey = "TEST-STATE-FILE";
    private static FileStatePersistence fileStatePersistence = null;
    private static Path dir;

    @BeforeAll
    static void setUp() throws IOException {
        dir = Files.createTempDirectory("file-state-persistence-test");
        fileStatePersistence = new FileStatePersistence(dir.toString());
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.delete(Paths.get(dir.toString(), testKey));
        Files.delete(dir);
    }

    @Test
    void saveStateToFile() {
        var serverList = Lists.asList("id-1", new String[] {"id-2"});
        var log = Lists.asList("entry-1", new String[] {"entry-2"});
        var state = new State("test-id", 10, "other-id", log, serverList);

        fileStatePersistence.save(testKey, state);
        var fileSaved = Files.exists(Paths.get(dir.toString(), testKey));

        assertTrue(fileSaved);

        var output = fileStatePersistence.load(testKey);

        assertEquals(state.getId(), output.getId());
    }

}
