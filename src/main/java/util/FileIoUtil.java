package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Exception.NotExistFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileIoUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileIoUtil.class);
    private static final String TEMPLATE_DIR = FileIoUtil.class.getClassLoader().getResource("./templates").getPath();
    private static final String STATIC_DIR = FileIoUtil.class.getClassLoader().getResource("./static").getPath();
    private static Set<String> templateTypes = new HashSet<>(Arrays.asList("html", "ico"));
    private static Set<String> staticTypes = new HashSet<>(Arrays.asList("css", "js", "eot", "svg", "ttf", "woff", "woff2", "png"));
    private static final String DOT = "\\.";
    private static final String URL_PARAM_DELIMITER = "?";
    public static String readBuffer(BufferedReader br, int length) {
        char[] data = new char[length];
        try {
            if (br.read(data) == -1) {
                throw new RuntimeException();
            }
            logger.debug("post query: " + String.valueOf(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(data);
    }

    public static String readLine(BufferedReader br) {
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }

    public static void isExistFile(String requestPath) throws NotExistFileException {
        Path path = mappingDirectoryPath(requestPath);
        if (!path.toFile().exists()) {
            throw new NotExistFileException("file xxx");
        }
    }

    public static String splitQuery(String path) throws RuntimeException {
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
