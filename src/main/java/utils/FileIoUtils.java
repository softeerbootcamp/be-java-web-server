package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileIoUtils {

    private static final String ERROR_PATH = "./templates/error404.html";

    private FileIoUtils() {
    }

    public static byte[] loadFile(String filePath) throws IOException {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
            return Files.readAllBytes(path);
        } catch (NullPointerException | URISyntaxException e) {
            return getEmptyBody();
        }
    }

    public static byte[] load404ErrorFile() throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(ERROR_PATH).toURI());
        return Files.readAllBytes(path);

    }

    public static byte[] getEmptyBody() {
        return "".getBytes();
    }

}
