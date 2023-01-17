package controller;

import httpMock.CustomHttpFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.StaticFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;

import static service.StaticFileService.getFileTypeFromUrl;

public class StaticFileController implements RequestController {
    private static Logger logger = LoggerFactory.getLogger(StaticFileController.class);

    private static final StaticFileController fileController = new StaticFileController();
    public static final String[] supportedFileTypes = {
            "html", "ico", "css", "js", "ttf", "woff", "svg", "eot", "woff2", "png", "jpeg", "gif"
    };

    public static StaticFileController get() {
        return fileController;
    }

    @Override
    public CustomHttpResponse handleRequest(CustomHttpRequest req) {
        return getFile(req);
    }

    public CustomHttpResponse getFile(CustomHttpRequest req) {
        File file = StaticFileService.getFile(req.getUrl());
        String fileType = StaticFileService.getFileTypeFromUrl(req.getUrl());
        ContentType contentType = ContentType.getContentTypeByFileType(fileType);
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            return CustomHttpResponse.of(StatusCode.OK, contentType, new HashMap<>(), data);
        } catch (IOException e) {
            return CustomHttpFactory.NOT_FOUND();
        }
    }

    public static boolean ifFileTypeRequested(String url) {
        String type = getFileTypeFromUrl(url);
        return Arrays.stream(supportedFileTypes).filter(item -> item.equals(type)).count() == 1L;
    }
}
