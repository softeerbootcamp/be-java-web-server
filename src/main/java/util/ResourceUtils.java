package util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ResourceUtils {
    public static byte[] loadFileFromClasspath(String filePath) throws IOException {
        Path path;
        try {
            path = Paths.get(Objects.requireNonNull(ResourceUtils.class.getClassLoader().getResource("./templates" + filePath)).toURI());
        } catch (URISyntaxException | NullPointerException e) {
            try {
                path = Paths.get(Objects.requireNonNull(ResourceUtils.class.getClassLoader().getResource("./static" + filePath)).toURI());
            } catch (URISyntaxException | NullPointerException ex) {
                return new byte[0];
            }
        }
        return Files.readAllBytes(path);
    }
}
