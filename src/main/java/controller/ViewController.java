package controller;

import model.general.ContentType;
import model.general.Header;
import model.general.Status;
import model.request.Request;
import model.response.Response;
import model.response.StatusLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ViewController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    private static final String templatePath = "./src/main/resources/templates";
    private static final String staticPath = "./src/main/resources/templates";

    @Override
    public Response getResponse(Request request) {
        if(request.getRequestLine().getUri().equals("/")) return getIndexHtmlWhenInputNothing(request);
        else if(request.getRequestLine().getUri().equals("/index.html")) return getIndexHtml(request);

        return Response.from(Status.NOT_FOUND);
    }

    private Response getIndexHtmlWhenInputNothing(Request request) {
        byte[] body = {};
        try {
            body = Files.readAllBytes(new File(templatePath + request.getRequestLine().getUri() + "index.html").toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        Map<Header, String> headers = response200Header(request.getRequestLine().getContentType(), body.length);

        return Response.of(StatusLine.from(Status.OK), headers, body);
    }

    private Response getIndexHtml(Request request) {
        byte[] body = {};
        try {
            body = Files.readAllBytes(new File(templatePath + request.getRequestLine().getUri()).toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        Map<Header, String> headers = response200Header(request.getRequestLine().getContentType(), body.length);

        return Response.of(StatusLine.from(Status.OK), headers, body);
    }

    private Map<Header, String> response200Header(ContentType contentType, int messageBodyLength) {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Content-Type"), contentType.getContentTypeValue() + ";charset=utf-8");
        headers.put(Header.from("Content-Length"), Integer.toString(messageBodyLength));

        return headers;
    }
}
