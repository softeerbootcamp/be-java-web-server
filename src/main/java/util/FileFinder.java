package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.WebServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileFinder {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    public static byte[] findFile(String uri) {
        try {
            return Files.readAllBytes(new File(uri).toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
            //TODO 나중에 exception 처리 해야함
        }
    }

    public static boolean isFind(String uri) {
        File file = new File(uri);
        if (file.exists()) {
            if (file.isFile()) {
                return true;
            }
            if (file.isDirectory()) {
                return false;
            }
        }
        return false;
    }
}
