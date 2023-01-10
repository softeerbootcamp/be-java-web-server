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
import java.util.HashMap;
import java.util.Map;

public class StaticFileService implements RequestService {
    private static final StaticFileService fileService = new StaticFileService();

    public static StaticFileService get() {
        return fileService;
    }

    private Map<String, RequestService> routingTable = new HashMap<>() {{
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
            URL resoucePath = getClass().getClassLoader().getResource("./static");
            String fileType = getFileTypeFromUrl(req.getUrl());
            res.setContentType(ContentType.getContentTypeByFileType(fileType));

            if (req.getUrl().endsWith("ico") || req.getUrl().endsWith("html")) {
                resoucePath = getClass().getClassLoader().getResource("./templates");
            }

            filePath = resoucePath.getPath();
            byte[] file = Files.readAllBytes(new File(filePath + req.getUrl()).toPath());
            res.addToBody(ArrayUtils.toObject(file));
            res.setStatusCode(StatusCode.OK);

        } catch (IOException e) {
            logger.error("file not found at " + filePath + req.getUrl());
            throw new RuntimeException(e);
        }
    }

    private String getFileTypeFromUrl(String url) {
        int index = url.lastIndexOf(".");
        return url.substring(index + 1);
    }
}
