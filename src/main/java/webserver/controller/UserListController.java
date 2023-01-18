package webserver.controller;

import db.Database;
import model.User;
import model.request.Request;
import model.response.Response;
import util.AuthInterceptor;
import util.ViewResolver;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import static model.response.HttpStatusCode.*;

public class UserListController implements UserController{

    @Override
    public Response service(Request request) {

        if (AuthInterceptor.isAuthUser(request)) {
            try {
                int idx = 3;
                Collection<User> users = Database.findAll();

                StringBuilder sb = new StringBuilder();
                sb.append("<tr>");
                for (User user : users) {
                    sb.append("<th scope=\"row\">").append(idx).append("</th><td>").append(user.getUserId()).append("</td> <td>").append(user.getName()).append("</td> <td>").append(user.getEmail()).append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
                    idx++;
                }
                Path filePath = ViewResolver.findFilePath("/user/list.html");
                String fileData = new String(ViewResolver.findActualFile(filePath));
                fileData = fileData.replace("%userList%", URLDecoder.decode(sb.toString(), StandardCharsets.UTF_8));

                return Response.of(request.getHttpVersion(), OK, Map.of("Content-Type", Files.probeContentType(filePath)), fileData.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return Response.of(request.getHttpVersion(), NOT_FOUND, Map.of(), new byte[0]);
            }
        }
        return Response.of(request.getHttpVersion(), FOUND, Map.of("Location", "/user/login.html"), new byte[0]);
    }
}
