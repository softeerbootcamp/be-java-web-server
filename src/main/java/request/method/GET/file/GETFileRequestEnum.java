package request.method.GET.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.method.GET.handlers.GETHandler;
import response.HttpResponseStatus;
import response.Response;
import webserver.WebServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public enum GETFileRequestEnum {
    TEMPLATE(".html .ico", GETTemplateFileHandler.getInstance()),
    STATIC(".css .eot .svg .ttf .woff .woff2 .png .js", GETStaticFileHandler.getInstance());

    private static Logger logger = LoggerFactory.getLogger(WebServer .class);

    private final String url;

    private GETHandler handler;

    private GETFileRequestEnum(String url, GETHandler handler) {
        this.url = url;
        this.handler = handler;
    }

    public String getUrl() {
        return url;
    }

    public GETHandler getHandler() {
        return handler;
    }

    public boolean supportsRequestedFilePostfix(GETFileRequestEnum urlEnum, Request request) {
        String requestFilePostfix = request.getResourceFilePostfix();
        StringTokenizer stringTokenizer = new StringTokenizer(urlEnum.getUrl()," ");
        while(stringTokenizer.hasMoreTokens()) {
            if(requestFilePostfix.equals(stringTokenizer.nextToken())) {
                return true;
            }
        }
        return false;
    }
}
