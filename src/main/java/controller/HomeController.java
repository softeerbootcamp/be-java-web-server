package controller;

import model.general.Header;
import model.general.Status;
import model.request.RequestLine;
import model.response.Response;
import model.response.StatusLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HomeController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private static final String fileParentPath = "src/main/resources/templates";

    @Override
    public Response getResponse(RequestLine requestLine) {
        if(requestLine.getUri().equals("index.html")) return getIndexHtml(requestLine);
        else return Response.from(Status.NOT_FOUND);
    }

    private Response getIndexHtml(RequestLine requestLine) {
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.of("Content-Type"), "text/html;charset=utf-8");

        byte[] body = {};
        try {
            body = Files.readAllBytes(new File(fileParentPath + requestLine.getUri()).toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        headers.put(Header.of("Content-Length"), Integer.toString(body.length));

        return Response.of(StatusLine.from(Status.OK), headers, body);
    }
}
