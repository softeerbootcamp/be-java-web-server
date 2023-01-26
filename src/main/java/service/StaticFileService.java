package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StaticFileService {
    private static final String STATIC_PATH = StaticFileService.class.getClassLoader().getResource("./static").getPath();
    private static final String TEMPLATE_PATH = StaticFileService.class.getClassLoader().getResource("./templates").getPath();
    private static final List<String> TEMPLATE_FILES = List.of("html");
    private static final Logger logger = LoggerFactory.getLogger(StaticFileService.class);
    public static String getFileTypeFromUrl(String url) {
        int index = url.lastIndexOf(".");
        return url.substring(index + 1);
    }

    public static String renderFile(File htmlFile, Map<String, String> matchings) throws FileNotFoundException {
        String originalFile = readFileToStr(htmlFile);

        for (String key : matchings.keySet()) {
            originalFile = originalFile.replace(("{:" + key + ":}"), matchings.get(key));
        }
        originalFile = originalFile.replaceAll("\\{:\\w+:\\}", "");
        return originalFile;
    }

    public static boolean isTemplateFile(String url) {
        return TEMPLATE_FILES.contains(getFileTypeFromUrl(url));
    }

    public static String readFileToStr(File file) throws FileNotFoundException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        return br.lines().collect(Collectors.joining("\n"));
    }

    public static File getFile(String url) {
        String filePath = STATIC_PATH + url;
        String fileType = getFileTypeFromUrl(url);
        if (TEMPLATE_FILES.contains(fileType)) {
            filePath = TEMPLATE_PATH + url;
        }
        return new File(filePath);
    }
}
