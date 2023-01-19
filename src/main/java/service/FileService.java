package service;

import enums.ContentType;
import enums.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import webserver.RequestHandler;
import utils.ExtensionUtils;
import utils.FileIoUtils;


public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";

    public static void serveFile(String url, HttpResponse res) throws NullPointerException {
        ContentType contentType = ExtensionUtils.extractExtension(url);
        res.setStatus(HttpStatus.OK);
        res.setContentType(contentType);
        byte[] body = FileIoUtils.loadFileFromClasspath(url);
        res.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
        res.setBody(body);//body에는 요청한 파일 내용이 들어감
    }

}
