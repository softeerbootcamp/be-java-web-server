package service;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import webserver.RequestService;
import webserver.httpMock.CustomHttpRequest;
import webserver.httpMock.CustomHttpResponse;
import webserver.httpMock.constants.ContentType;
import webserver.httpMock.constants.StatusCode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticFileService implements RequestService {
    private static final StaticFileService fileService = new StaticFileService();
    public static final String[] supportedFileTypes = {
            "html", "ico", "css", "js", "ttf", "woff", "svg", "eot", "woff2", "png", "jpeg", "gif"
    };
    public static StaticFileService get() {
        return fileService;
    }

    private final Map<String, RequestService> routingTable = new HashMap<>() {{
        put(".+", (req, res) -> getFile(req, res));
    }};

    @Override
    public void handleRequest(CustomHttpRequest req, CustomHttpResponse res) {
        for (String regex : routingTable.keySet()) {
            if (req.getUrl().matches(regex)) {
                routingTable.get(regex).handleRequest(req, res);
                return;
            }
        }
        RequestService.NOT_FOUND(res);
    }

    public void getFile(CustomHttpRequest req, CustomHttpResponse res) {
        String filePath = null;
        try {
            URL resourcePath = getClass().getClassLoader().getResource("./static");
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
            RequestService.NOT_FOUND(res);
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
