package util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ResourceUtils {

    private final static String PATH_OF_TEMPLATE = "./templates";
    private final static String PATH_OF_STATIC = "./static";

    public static byte[] loadFileFromClasspath(String filePath) {
        Path path = search(PATH_OF_TEMPLATE + filePath);
        if (path == null) {
            path = search(PATH_OF_STATIC + filePath);
            if (path == null) {
                return new byte[0];
            }
        }

        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path search(String filePath) {
        try {
            return Paths.get(Objects.requireNonNull(ResourceUtils.class.getClassLoader().getResource(filePath)).toURI());
        } catch (URISyntaxException | NullPointerException e) {
            return null;
        }
    }
}
