package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.File;
import java.util.List;

public class StaticFileService {
    private static final String STATIC_PATH = StaticFileService.class.getClassLoader().getResource("./static").getPath();
    private static final String TEMPLATE_PATH = StaticFileService.class.getClassLoader().getResource("./templates").getPath();

    private static final List<String> TEMPLATE_FILES = List.of("html", "ico");
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static String getFileTypeFromUrl(String url) {
        int index = url.lastIndexOf(".");
        return url.substring(index + 1);
    }

    public static File getFile(String url){
        String filePath = STATIC_PATH + url;
        String fileType = getFileTypeFromUrl(url);
        if (TEMPLATE_FILES.contains(fileType)) {
            filePath = TEMPLATE_PATH + url;
        }
        return new File(filePath);
    }
}
