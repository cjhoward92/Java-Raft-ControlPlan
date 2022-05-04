package com.examples.raft.config;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystemConfigProvider implements ConfigProvider {

    private static final Logger logger = LogManager.getLogger(FileSystemConfigProvider.class);
    private static final String CONFIG_PATH = "/tmp/raft/config.json";

    @Override
    public ClusterConfig getConfig() {
        logger.info("Loading config from file system");
        try {
            var configPath = Paths.get(CONFIG_PATH);
            if (!Files.exists(configPath)) {
                logger.warn("File {} does not exist!", configPath);
                return null;
            }

            String json = String.join("", Files.readAllLines(configPath));
            var gson = new Gson();
            return gson.fromJson(json, ClusterConfig.class);
        } catch (IOException ex) {
            logger.error("Error reading config:", ex);
            return null;
        }
    }
}
