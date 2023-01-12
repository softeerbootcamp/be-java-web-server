package util;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpUri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HttpResponseUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtils.class);

    private static final String basePath = "./src/main/resources";
    private static final String htmlFilePath = "/templates";
    private static final String staticFilePath = "/static";

    public static String makeFilePath(String contentType) {
        if(contentType.equals("text/html")){
            return basePath + htmlFilePath;
        }
        return basePath + staticFilePath;
    }

    public static byte[] makeBody(String httpUri, String filePath) throws IOException {
        logger.debug("filePath get : {}", filePath + httpUri);
        return Files.readAllBytes(new File(filePath + httpUri).toPath());
    }

}
