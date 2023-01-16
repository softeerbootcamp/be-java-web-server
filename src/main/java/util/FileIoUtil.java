package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileIoUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtil.class);
    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final String TEMPLATE_DIR = PROJECT_DIR + "/src/main/resources/templates";
    private static final String STATIC_DIR = PROJECT_DIR + "/src/main/resources/static";
    private static Set<String> templateTypes = new HashSet<>(Arrays.asList("html", "ico"));
    private static Set<String> staticTypes = new HashSet<>(Arrays.asList("css", "js", "eot", "svg", "ttf", "woff", "woff2", "png"));
    private static final String DOT = "\\.";
    private static final String URL_PARAM_DELIMITER = "?";

    public static String readBuffer(BufferedReader br, int length) {
        char[] data = new char[length];
        try {
            if(br.read(data) == -1){
                throw new RuntimeException();
            }
            logger.debug("post query: " + String.valueOf(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(data);
    }
    public static String readLine(BufferedReader br){
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  line;
    }
    public static String splitQuery(String path) throws RuntimeException{
        if (path.startsWith("/user/create")) {
            int idx = path.indexOf(URL_PARAM_DELIMITER);
            return path.substring(idx + 1);
        }
        throw new RuntimeException();
    }

    public static Path mappingDirectoryPath(String path) throws NullPointerException {
        String ex = findExtension(path);
        if (templateTypes.contains(ex)) return new File(TEMPLATE_DIR + path).toPath();
        if (staticTypes.contains(ex)) return new File(STATIC_DIR + path).toPath();
        return null;
    }

    public static String findExtension(String path) {
        String[] temp = path.split(DOT);
        String extension = temp[temp.length - 1];
        return extension;
    }

}
