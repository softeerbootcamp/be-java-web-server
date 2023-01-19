package webserver.controller;

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

public class IndexController {

    public Response service(Request request) throws IOException {
        Path filePath = ViewResolver.findFilePath("index.html");
        if (AuthInterceptor.isAuthUser(request)) {
            BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()));

            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.contains("로그인") || line.contains("회원가입")) {
                    continue;
                }
                if (line.contains("class=\"dropdown-toggle\"")) {
                    String name = AuthInterceptor.findUser(request).getName();
                    sb.append("<a>").append(name).append("님</a></li><li>");
                }
                sb.append(line);
            }
            return Response.of(request.getHttpVersion(), OK, Map.of("Content-Type", Files.probeContentType(filePath)), sb.toString().getBytes());
        }
        return Response.of(request.getHttpVersion(), OK, Map.of("Content-Type", Files.probeContentType(filePath)), ViewResolver.findActualFile(filePath));
    }
}
