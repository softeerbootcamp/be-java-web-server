package webserver.controller;

import model.request.Request;
import model.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static model.response.HttpStatusCode.OK;
import static util.ViewResolver.findActualFile;
import static util.ViewResolver.findFilePath;

public class FrontServlet {

    private final Map<String, WasHandlerAdapter> adapterMap = new HashMap<>();

    public FrontServlet() {
        adapterMap.put("user", new UserHandlerAdapter());
    }

    public Response process(Request request) {
        try {
            Path path = findFilePath(request.getUrl());

            if (request.getUrl().contains(".html")) {
                DynamicHtmlController dynamicHtmlController = new DynamicHtmlController();
                return dynamicHtmlController.service(request);
            }

            String contentType = Files.probeContentType(path)==null ? "*/*" : Files.probeContentType(path);
            return Response.of(request.getHttpVersion(), OK, Map.of("Content-Type", contentType), findActualFile(path));

        } catch (IOException e) {
            String[] split = request.getUrl().split("/");
            WasHandlerAdapter wasHandlerAdapter = adapterMap.get(split[1]);
            return wasHandlerAdapter.process(request);
        }
    }
}
