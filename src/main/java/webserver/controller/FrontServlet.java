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
            return Response.of(request.getHttpVersion(), OK, Map.of("Content-Type", Files.probeContentType(path)), findActualFile(path));

        } catch (IOException e) {
            String[] split = request.getUrl().split("/");
            WasHandlerAdapter wasHandlerAdapter = adapterMap.get(split[1]);
            return wasHandlerAdapter.process(request);
        } catch (NullPointerException exception) {
            //TODO Header Map에 content-type 넣을 때 NullPointerException 발생해서 임시방편으로 처리해둠.
            System.out.println(request.getUrl());
            return Response.of(request.getHttpVersion(), OK, Map.of(),new byte[0]);
        }
    }
}
