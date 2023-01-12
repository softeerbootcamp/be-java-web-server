package controller;

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

    private static final String fileParentPath = "./src/main/resources/templates";

    @Override
    public Response getResponse(Request request) {
        if(request.getRequestLine().getUri().equals("/")) return getIndexHtmlWhenInputNothing(request);
        else if(request.getRequestLine().getUri().equals("/index.html")) return getIndexHtml(request);

        return Response.from(Status.NOT_FOUND);
    }

    private Response getIndexHtmlWhenInputNothing(Request request) {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Content-Type"), "text/html;charset=utf-8");

        byte[] body = {};
        try {
            body = Files.readAllBytes(new File(fileParentPath + request.getRequestLine().getUri() + "index.html").toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        headers.put(Header.from("Content-Length"), Integer.toString(body.length));

        return Response.of(StatusLine.from(Status.OK), headers, body);
    }

    private Response getIndexHtml(Request request) {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.from("Content-Type"), "text/html;charset=utf-8");

        byte[] body = {};
        try {
            body = Files.readAllBytes(new File(fileParentPath + request.getRequestLine().getUri()).toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        headers.put(Header.from("Content-Length"), Integer.toString(body.length));

        return Response.of(StatusLine.from(Status.OK), headers, body);
    }
}
