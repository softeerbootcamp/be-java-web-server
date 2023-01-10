package controller;

import db.StaticFileService;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.util.Arrays;

import static db.StaticFileService.getFileTypeFromUrl;

public class StaticFileController implements RequestController {
    private static final StaticFileController fileController = new StaticFileController();
    public static final String[] supportedFileTypes = {
            "html", "ico", "css", "js", "ttf", "woff", "svg", "eot", "woff2", "png", "jpeg", "gif"
    };

    public static StaticFileController get() {
        return fileController;
    }

    @Override
    public void handleRequest(CustomHttpRequest req, CustomHttpResponse res) {
        getFile(req, res);
    }

    public void getFile(CustomHttpRequest req, CustomHttpResponse res) {
        File file = StaticFileService.getFile(req.getUrl());
        String fileType = StaticFileService.getFileTypeFromUrl(req.getUrl());
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            res.setStatusCode(StatusCode.OK);
            res.setContentType(ContentType.getContentTypeByFileType(fileType));
            res.addToBody(data);
        } catch (IOException e) {
            RequestController.NOT_FOUND(res);
        }
    }

    public static boolean isFileTypeSupported(String url) {
        String type = getFileTypeFromUrl(url);
        return Arrays.stream(supportedFileTypes).filter(item -> item.equals(type)).count() == 1L;
    }
}
