package utils;

import java.io.File;
import java.nio.file.Files;

public class FileIoUtils {
    private static final String STATIC_RESOURCE_PATH = FileIoUtils.class.getClassLoader().getResource("./static").getPath();
    private static final String TEMPLATE_RESOURCE_PATH = FileIoUtils.class.getClassLoader().getResource("./template").getPath();

    public static byte[] loadFile(String file) {
        try {
            if (file.contains("html") || file.contains("ico"))
                return Files.readAllBytes(new File(TEMPLATE_RESOURCE_PATH + file).toPath());
            return Files.readAllBytes(new File(STATIC_RESOURCE_PATH + file).toPath());
        } catch (Exception e) {
            return StatusCode.NOTFOUND.toString().getBytes();
        }
    }
}
