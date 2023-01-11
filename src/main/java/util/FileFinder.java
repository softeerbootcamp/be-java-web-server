package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileFinder {

    public static byte[] findFile(String uri) throws IOException {
        return Files.readAllBytes(new File("./src/main/resources/" + uri).toPath());
    }

}
