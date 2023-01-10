package util;

import java.io.File;
import java.nio.file.Path;

public class FileIoUtil {
    private static final String TEMPLATE_DIR = "./src/main/resources/templates";
    private static final String STATIC_DIR = "./src/main/resources/static";

    public static Path mappingPath(String path) {
        String ex = findExtension(path);
        if(checkTemplate(ex)) return new File(TEMPLATE_DIR+path).toPath();
        if(checkStatic(ex)) return new File(STATIC_DIR+path).toPath();

        return null;
    }
    public static String findExtension(String path){
        String[] temp = path.split("\\.");
        String extension = temp[temp.length - 1];
        return extension;
    }
    public static boolean checkTemplate(String ex){
        return ex.equals("html") || ex.equals("ico");
    }
    public static boolean checkStatic(String ex){
        return ex.equals("html") || ex.equals("ico");
    }

}
