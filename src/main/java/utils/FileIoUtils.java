package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileIoUtils {
    private static final String STATIC_RESOURCE_PATH = "src/main/resources/static";
    private static final String TEMPLATE_RESOURCE_PATH = "src/main/resources/templates";
    public static byte[] loadFile(String file) throws IOException {
        try {
            if (file.contains("html") || file.contains("ico"))
                return Files.readAllBytes(new File(TEMPLATE_RESOURCE_PATH + file).toPath());
            return Files.readAllBytes(new File(STATIC_RESOURCE_PATH + file).toPath());
        }
        catch (Exception e) {
            return Files.readAllBytes(new File(TEMPLATE_RESOURCE_PATH + "/index.html").toPath());
        }
    }
}
