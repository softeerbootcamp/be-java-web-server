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

    public void process(Request request, Response response) {
        try {
            Path path = findFilePath(request.getUrl());
            response.addHeader("Content-Type", Files.probeContentType(path));
            response.setBody(findActualFile(path));
            response.setStatusCode(request.getHttpVersion(), OK);

        } catch (IOException e) {
            String[] split = request.getUrl().split("/");
            WasHandlerAdapter wasHandlerAdapter = adapterMap.get(split[1]);
            wasHandlerAdapter.process(request, response);
        }
    }
}
