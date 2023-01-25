package request.handlers;

import file.FileContentType;
import model.User;
import request.Request;
import request.RequestHandler;
import response.HttpResponseStatus;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Collection;

public class UserListHandler implements RequestHandler {
    private static final UserListHandler instance;

    static {
        instance = new UserListHandler();
    }

    private UserListHandler() {}

    public static UserListHandler getInstance() {
        return instance;
    }

    @Override
    public Response doGet(Request request) {
        try {
            if(!sessionService.isValid(request.getCookie())) {
                return Response.createFullResponse(
                        HttpResponseStatus.FOUND.getMessage().getBytes(),
                        request.getResourceFileContentType(),
                        HttpResponseStatus.FOUND,
                        "Location: /user/login.html\r\n");
            }
            String resource = request.getResource();
            if(!resource.endsWith(".html")) {
                resource += ".html";
            }
            byte[] file = generateDynamicHeader(request.getCookie(),
                    Files.readAllBytes(new File("src/main/resources/templates" + resource).toPath()));
            file = generateDynamicList(file);
            return Response.createSimpleResponse(file, FileContentType.HTML.getContentType(), HttpResponseStatus.OK);
        } catch (IOException e) {
            return Response.from(HttpResponseStatus.NOT_FOUND);
        } catch (SQLException | NullPointerException e) {
            return Response.from(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] generateDynamicList(byte[] file) {
        try {
            String fileString = new String(file);
            Collection<User> users = userService.findAllUsers();
            StringBuilder stringBuilder = new StringBuilder();
            int no = 1;
            for (User user : users) {
                stringBuilder.append("<tr><th scope=\"row\">" + (no++) +
                        "</th> <td>" + user.getUserId() +
                        "</td> <td>" + user.getName() +
                        "</td> <td>" + user.getEmail() +
                        "</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>");
            }
            fileString = fileString.replace("${}", stringBuilder.toString());
            return fileString.getBytes();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
