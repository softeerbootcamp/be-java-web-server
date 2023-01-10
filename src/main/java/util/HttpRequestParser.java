package util;

import java.io.File;
import java.nio.file.Path;

public class HttpRequestParser {
    public static Path mappingPath(String path) {
        String dir = "./src/main/resources/";
        String ex = findExtension(path);
        String targetDir = checkExtension(ex) ? "templates" : "static";
        return new File(dir+targetDir+path).toPath();
    }
    public static String findExtension(String path){
        String[] temp = path.split("\\.");
        String extension = temp[temp.length - 1];
        return extension;
    }
    public static boolean checkExtension(String ex){
        return ex.equals("html") || ex.equals("ico");
    }
}
