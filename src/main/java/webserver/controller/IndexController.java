package webserver.controller;

import model.request.Request;
import model.response.Response;
import util.AuthInterceptor;
import util.ViewResolver;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static model.response.HttpStatusCode.OK;

public class IndexController {

    public Response service(Request request) throws IOException {
        String data;
        if (AuthInterceptor.isAuthUser(request)) {
            String name = AuthInterceptor.findUser(request).getName();
            data = "<li ><a href = \"index.html#\" role = \"button\" >" + name + " </a ></li >";

        } else {
            data = "<li ><a href = \"user/login.html\" role = \"button\" > 로그인 </a ></li >";
        }
        Path filePath = ViewResolver.findFilePath("index.html");
        String fileData = new String(ViewResolver.findActualFile(filePath));
        fileData = fileData.replace("%login%", URLDecoder.decode(data, StandardCharsets.UTF_8));

        return Response.of(request.getHttpVersion(), OK, Map.of("Content-Type", Files.probeContentType(filePath)), fileData.getBytes());
    }
}
