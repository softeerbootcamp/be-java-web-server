package request.handlers;

import file.FileContentType;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestHandler;
import response.HttpResponseStatus;
import response.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

public class UserListHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserListHandler.class);

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
            String filePath = "src/main/resources/templates" + resource;
            String content = generateDynamicHeader(request.getCookie(), filePath);
            content = generateDynamicList(content);
            return Response.createSimpleResponse(content.getBytes(), FileContentType.HTML.getContentType(), HttpResponseStatus.OK);
        } catch (IOException e) {
            return Response.from(HttpResponseStatus.NOT_FOUND);
        } catch (SQLException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            return Response.from(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateDynamicList(String fileContent) throws SQLException {
        Collection<User> users = userService.findAllUsers();
        StringBuilder stringBuilder = new StringBuilder();
        int no = 1;
        for (User user : users) {
            stringBuilder.append(
                    "<tr>" +
                            "<th scope=\"row\">" + no + "</th> " +
                            "<td>" + user.getId() + "</td>" +
                            "<td>" + user.getUsername() + "</td>" +
                            "<td>" + user.getEmail() + "</td>" +
                            "<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>" +
                    "</tr>");
            no++;
        }
        fileContent = fileContent.replace("${userList}", stringBuilder.toString());
        return fileContent;
    }
}
