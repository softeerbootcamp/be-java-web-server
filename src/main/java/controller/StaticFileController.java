package controller;

import Utility.HtmlMakerUtility;
import httpMock.CustomHttpFactory;
import httpMock.CustomHttpRequest;
import httpMock.CustomHttpResponse;
import httpMock.constants.ContentType;
import httpMock.constants.StatusCode;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.PostService;
import service.SessionService;
import service.StaticFileService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static service.StaticFileService.getFileTypeFromUrl;

public class StaticFileController implements RequestController {
    private static final Logger logger = LoggerFactory.getLogger(StaticFileController.class);

    private static final StaticFileController fileController = new StaticFileController();
    public static final String[] supportedFileTypes = {
            "html", "ico", "css", "js", "ttf", "woff", "svg", "eot", "woff2", "png", "jpeg", "gif"
    };

    public static StaticFileController get() {
        return fileController;
    }

    @Override
    public CustomHttpResponse handleRequest(CustomHttpRequest req) {
        if (!ifFileTypeRequested(req.getUrl()))
            req.setUrl(req.getUrl() + ".html");
        if (req.getUrl().endsWith("index.html"))
            return getMainPage(req);
        logger.debug("req {} comes static handler", req.getUrl());
        return getFile(req);
    }

    private CustomHttpResponse getMainPage(CustomHttpRequest req) {
        File file = StaticFileService.getFile(req.getUrl());
        User user = SessionService.getUserBySessionId(req.getSSID()).orElse(User.GUEST);
        try {
            Map<String, String> matching = HtmlMakerUtility.getDefaultTemplate(user.getName());
            matching.put("postList", HtmlMakerUtility.postListRows(PostService.findAll()));
            String lines = StaticFileService.renderFile(file, matching);

            return CustomHttpFactory.OK_HTML(lines);
        } catch (FileNotFoundException e) {
            return CustomHttpFactory.NOT_FOUND();
        }
    }

    private CustomHttpResponse getFile(CustomHttpRequest req) {
        if (StaticFileService.isTemplateFile(req.getUrl()))
            return handleTemplateFile(req);
        return handleStaticFile(req);
    }

    private CustomHttpResponse handleTemplateFile(CustomHttpRequest req) {
        File file = StaticFileService.getFile(req.getUrl());
        User user = SessionService.getUserBySessionId(req.getSSID()).orElse(User.GUEST);

        try {
            Map<String, String> matching = HtmlMakerUtility.getDefaultTemplate(user.getName());
            String lines = StaticFileService.renderFile(file, matching);

            return CustomHttpFactory.OK_HTML(lines);
        } catch (IOException e) {
            return CustomHttpFactory.NOT_FOUND();
        }
    }

    public CustomHttpResponse handleStaticFile(CustomHttpRequest req) {
        File file = StaticFileService.getFile(req.getUrl());
        String fileType = StaticFileService.getFileTypeFromUrl(req.getUrl());
        ContentType contentType = ContentType.getContentTypeByFileType(fileType);
        try {
            return CustomHttpResponse.of(StatusCode.OK, contentType, new HashMap<>(), Files.readAllBytes(Path.of(file.getPath())));
        } catch (IOException e) {
            return CustomHttpFactory.NOT_FOUND();
        }
    }

    public static boolean ifFileTypeRequested(String url) {
        String type = getFileTypeFromUrl(url);
        return Arrays.stream(supportedFileTypes).filter(item -> item.equals(type)).count() == 1L;
    }
}
