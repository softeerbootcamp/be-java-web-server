package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    public static byte[] loadFileFromClasspath(String filePath) throws NullPointerException, IOException, URISyntaxException, URISyntaxException, IOException {
        URL fileUrl = FileIoUtils.class.getClassLoader().getResource(filePath);
//        if (fileUrl == null) {
//            throw new FileNotFoundException();
//        }
        Path path = Paths.get(fileUrl.toURI());
        return Files.readAllBytes(path);
    }
}
