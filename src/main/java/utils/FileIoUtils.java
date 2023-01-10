package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class FileIoUtils {
    public static final String TEMPLATES_PREFIX = "./templates";
    public static final String STATIC_PREFIX = "./static";

    private FileIoUtils() {}

    public static byte[] loadFile(String filename) throws IOException, URISyntaxException {
        return Files.readAllBytes(getPath(String.valueOf(filename)));
    }

    private static Path getPath(String path) throws URISyntaxException {
        if(path.endsWith("html")){
            return Paths.get(Objects.requireNonNull(FileIoUtils.class.getClassLoader().getResource(TEMPLATES_PREFIX + path)).toURI());
        }
        return Paths.get(Objects.requireNonNull(FileIoUtils.class.getClassLoader().getResource(STATIC_PREFIX + path)).toURI());
    }
}
