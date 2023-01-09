package util;

import java.io.File;
import java.nio.file.Path;

public class HttpRequestParser {
    private static final String dir = "./src/main/resources/";
    public static Path mappingPath(String path) {
        String[] temp = path.split("//.");
        String extension = temp[temp.length - 1];

        String targetDir = extension.equals("html") ? "templates" : "static";
        return new File(dir+targetDir+path).toPath();
    }
}
