package util;

import http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class HttpResponseUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtils.class);

    public static byte[] makeBody(String httpUri, String filePath) {
        logger.debug("filePath get : {}", filePath + httpUri);
        try {
            return Files.readAllBytes(new File(filePath + httpUri).toPath());
        } catch (IOException e) {
            return " 파일을 찾지 못함 !".getBytes();
        }
    }

    public static HttpHeader makeResponse200Header(String contentType, int bodyLength) {
        List<String> headerLines = new ArrayList<>();
        logger.debug("contentType: {}", contentType);
        headerLines.add("Content-Type: " + contentType + ";charset=utf-8" + System.lineSeparator());
        headerLines.add("Content-Length: " + bodyLength + System.lineSeparator());
        return new HttpHeader(headerLines);
    }

    public static HttpHeader makeResponse302Header(String destination) {
        List<String> headerLines = new ArrayList<>();
        headerLines.add("Location: " + destination + System.lineSeparator());
        return new HttpHeader(headerLines);
    }

}
