package com.examples.raft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class FileStatePersistence implements StatePersistence {

    private static final Logger logger = LogManager.getLogger(FileStatePersistence.class);

    private final String dir;

    public FileStatePersistence(final String dir) {
        this.dir = dir;
    }

    @Override
    public boolean save(final String key, final State state) {

        try {
            var stateFilePath = Paths.get(this.dir, key);
            if (!Files.exists(stateFilePath)) {
                Files.createFile(stateFilePath);
            }

            // Use GSON to convert to-from JSON
            var gson = new Gson();
            String json = gson.toJson(state);
            Files.writeString(stateFilePath, json);

            return true;
        } catch (IOException ex) {
            logger.error("Error saving state:", ex);
            return false;
        }
    }

    @Override
    public State load(final String key) {
        try {
            var stateFilePath = Paths.get(this.dir, key);
            if (!Files.exists(stateFilePath)) {
                logger.warn("File {} does not exist!", stateFilePath);
                return null;
            }

            String json = String.join("", Files.readAllLines(stateFilePath));
            var gson = new Gson();
            return gson.fromJson(json, State.class);
        } catch (IOException ex) {
            logger.error("Error reading state:", ex);
            return null;
        }
    }
}
