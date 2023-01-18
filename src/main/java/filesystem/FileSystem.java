package filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileSystem {

    private FileSystem() {
    }

    public static FindResource findResource(String url) {
        String resourcePath = PathResolver.parse(url);
        byte[] resource = readFile(new File(resourcePath));

        FindResource findResult = new FindResource(resourcePath, resource);
        return findResult;
    }

    private static byte[] readFile(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
