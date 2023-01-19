package webserver.controller;

import exception.SessionExpiredException;
import exception.SessionNotFoundException;
import model.request.Request;
import model.response.Response;
import util.AuthInterceptor;
import util.ViewResolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static model.response.HttpStatusCode.OK;

public class DynamicHtmlController {

    public Response service(Request request) throws IOException {
        Path filePath = ViewResolver.findFilePath(request.getUrl());
        BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()));
        String line;
        StringBuilder body = new StringBuilder();

        if (AuthInterceptor.isAuthUser(request)) {
            while ((line = br.readLine()) != null) {
                if (line.contains("로그인") || line.contains("회원가입")) {
                    continue;
                }
                if (line.contains("class=\"dropdown-toggle\"")) {
                    try {
                        String name = AuthInterceptor.findUserSession(request).getName();
                        body.append("<a>").append(name).append("님</a></li><li>");

                    } catch (SessionExpiredException | SessionNotFoundException e) {
                        body.append(line);
                    }
                }
                body.append(line);
            }
            return Response.of(request.getHttpVersion(), OK, Map.of("Content-Type", Files.probeContentType(filePath)), body.toString().getBytes());
        }

        while ((line = br.readLine()) != null) {
            if (line.contains("로그아웃")) {
                continue;
            }
            body.append(line);
        }
        return Response.of(request.getHttpVersion(), OK, Map.of("Content-Type", Files.probeContentType(filePath)), body.toString().getBytes());
    }
}
