package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    public static byte[] loadFileFromClasspath(String filePath) throws NullPointerException {
        byte[] res = new byte[0];
        try {
            URL fileUrl = FileIoUtils.class.getClassLoader().getResource(filePath);
            Path path = Paths.get(fileUrl.toURI());
            res = Files.readAllBytes(path);
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return res;

    }
}
