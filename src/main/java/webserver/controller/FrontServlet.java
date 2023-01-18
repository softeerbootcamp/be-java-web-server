package webserver.controller;

import model.request.Request;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.AuthInterceptor;
import webserver.RequestHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static model.response.HttpStatusCode.OK;
import static util.ViewResolver.findActualFile;
import static util.ViewResolver.findFilePath;

public class FrontServlet {

    private static final Logger logger = LoggerFactory.getLogger(FrontServlet.class);

    private final Map<String, WasHandlerAdapter> adapterMap = new HashMap<>();

    public FrontServlet() {
        adapterMap.put("user", new UserHandlerAdapter());
    }

    public Response process(Request request) {
        try {
            Path path = findFilePath(request.getUrl());

            //TODO 인덱스.html마저 여기서...? 이거 너무 쓰레기같다. 컨트롤러 구조 고민필요.
            if (request.getUrl().contains("index.html")) {
                IndexController indexController = new IndexController();
                return indexController.service(request);
            }

            if (request.getUrl().contains("user/list.html")) {
                UserListController userListController = new UserListController();
                return userListController.service(request);
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
