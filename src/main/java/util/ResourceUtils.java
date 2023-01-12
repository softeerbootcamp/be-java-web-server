package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ResourceUtils {
    private static final Logger logger = LoggerFactory.getLogger(ResourceUtils.class);

    private static final String PATH_OF_TEMPLATE = "./templates";
    private static final String PATH_OF_STATIC = "./static";

    public static byte[] loadFileFromClasspath(String filePath) {
        try {
            Path path = search(PATH_OF_TEMPLATE + filePath);
            if (Objects.nonNull(path)) {
                return Files.readAllBytes(path);
            }

            path = search(PATH_OF_STATIC + filePath);
            if (Objects.nonNull(path)) {
                return Files.readAllBytes(path);
            }
        } catch (IOException e) {
            logger.debug("[IOException - ResourceUtils] - path : {}", filePath);
        }
        return new byte[0];
    }

    private static Path search(String filePath) {
        try {
            return Paths.get(Objects.requireNonNull(ResourceUtils.class.getClassLoader().getResource(filePath)).toURI());
        } catch (URISyntaxException | NullPointerException e) {
            return null;
        }
    }
}
