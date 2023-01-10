package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileIoUtils {
    public static final String TEMPLATES_PREFIX = "./templates";
    public static final String STATIC_PREFIX = "./static";

    private FileIoUtils() {
    }

    public static byte[] loadFile(String filename) throws IOException, URISyntaxException {
        return Files.readAllBytes(getPath(String.valueOf(filename)));
    }

    private static Path getPath(String path) throws URISyntaxException {
        try {
            if (path.endsWith("html")) {
                return Paths.get(FileIoUtils.class.getClassLoader().getResource(TEMPLATES_PREFIX + path).toURI());
            }
            return Paths.get(FileIoUtils.class.getClassLoader().getResource(STATIC_PREFIX + path).toURI());
        } catch (NullPointerException e) {
            throw new NullPointerException("파일이 존재하지 않습니다.");
        }
    }
}
