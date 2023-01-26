package request;

import response.HttpResponseStatus;
import response.Response;
import service.PostService;
import service.SessionService;
import service.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public interface RequestHandler {
    UserService userService = UserService.getInstance();

    SessionService sessionService = SessionService.getInstance();

    PostService postService = PostService.getInstance();

    default Response doGet(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    default Response doPost(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    default Response doPut(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    default Response doDelete(Request request) {
        return Response.from(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }

    default String generateDynamicHeader(String cookie, String filePath) throws IllegalArgumentException, SQLException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }
        String content = stringBuilder.toString();
        boolean isLoggedIn = sessionService.findSession(cookie).isPresent();

        content = content.replace("${userId}", isLoggedIn ? "\"<li class=\"dropdown-toggle\" data-toggle=\"dropdown\"><a href=\"#\">" + sessionService.findSession(cookie).get().getUserId() + "</a></li>\n" : "");

        String headerRegEx = "\\$\\{header}.*?\\$\\{/header}";

        String headerWithSession =
                "<li class=\"active\"><a href=\"index.html\">Posts</a></li>\n" +
                "<li><a href=\"/user/logout\" role=\"button\">로그아웃</a></li>\n" +
                "<li><a href=\"#\" role=\"button\">개인정보수정</a></li>\n";

        String headerWithoutSession =
                "<li class=\"active\"><a href=\"index.html\">Posts</a></li>\n" +
                "<li><a href=\"/user/login.html\" role=\"button\">로그인</a></li>\n" +
                "<li><a href=\"/user/create.html\" role=\"button\">회원가입</a></li>\n";

        content = content.replaceFirst(headerRegEx, isLoggedIn ? headerWithSession : headerWithoutSession);

        return content;
    }
}
