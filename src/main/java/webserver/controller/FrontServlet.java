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

            //모든 html 파일에 일관되게 동적 html 포맷 (사용자 이름 표시, 비로그인 시 로그아웃 버튼 삭제, 로그인 시 회원가입, 로그인 버튼 삭제) 적용을 위해
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
