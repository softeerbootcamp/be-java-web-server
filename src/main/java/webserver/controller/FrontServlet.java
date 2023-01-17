package webserver.controller;

import model.request.Request;
import model.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static model.response.HttpStatusCode.OK;
import static webserver.ViewResolver.findActualFile;
import static webserver.ViewResolver.findFilePath;

public class FrontServlet {

    private final UserFrontServlet userFrontServlet = new UserFrontServlet();


    public void process(Request request, Response response) {

        try {
            Path path = findFilePath(request.getUrl());
            response.addHeader("Content-Type", Files.probeContentType(path));
            response.setBody(findActualFile(path));

            response.setStatusCode(request.getHttpVersion(), OK);
        } catch (IOException e) {
            if (request.getUrl().contains("user")) {
                userFrontServlet.process(request, response);
            }
        }
    }
}
