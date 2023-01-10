package controller;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;

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
        RequestController.NOT_FOUND(res);
    }

    public void getFile(CustomHttpRequest req, CustomHttpResponse res) {
        String filePath = null;
        URL resourcePath = getClass().getClassLoader().getResource("./static");

        try {
            String fileType = getFileTypeFromUrl(req.getUrl());
            res.setContentType(ContentType.getContentTypeByFileType(fileType));

            if (req.getUrl().endsWith("ico") || req.getUrl().endsWith("html")) {
                resourcePath = getClass().getClassLoader().getResource("./templates");
            }

            filePath = resourcePath.getPath();
            byte[] file = Files.readAllBytes(new File(filePath + req.getUrl()).toPath());
            res.addToBody(ArrayUtils.toObject(file));
            res.setStatusCode(StatusCode.OK);

        } catch (IOException e) {
            logger.error("file not found at " + filePath + req.getUrl());
            RequestController.NOT_FOUND(res);
        } catch (NullPointerException e) {
            logger.error("Cannot get resourcePath");
            throw new RuntimeException(e);
        }
    }

    public static String getFileTypeFromUrl(String url) {
        int index = url.lastIndexOf(".");
        return url.substring(index + 1);
    }

    public static boolean isFileTypeSupported(String url){
        String type = getFileTypeFromUrl(url);
        return Arrays.stream(supportedFileTypes).filter(item->item.equals(type)).count() == 1L;
    }
}
