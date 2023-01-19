package service;

import db.Database;
import http.HttpStatus;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseUtils;

import java.util.Collection;

public class ListService {
    private static final Logger logger = LoggerFactory.getLogger(ListService.class);

    public static HttpResponse service(User logInUser, String filePath, String httpVersion, String contentType) {
        logger.debug("logInUser : {}", logInUser);
        // Login check
        if (logInUser != null) return logInListService(logInUser, filePath, httpVersion, contentType);

        // not Login
        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.FOUND, httpVersion))
                .set302Header("/user/login.html")
                .build();
    }

    public static HttpResponse logInListService(User logInUser, String filePath, String httpVersion, String contentType) {
        StringBuilder stringBuilder = makeHtml();

        // 파일 경로를 넘겨서 http response string 생성
        String responseString = new String(HttpResponseUtils.makeBody(filePath));
        String addedUserListToresponseString = responseString.replace("%userList%", stringBuilder.toString());
        byte[] responseBody = HtmlService.banLogInAndSignUpWhenUserLogInState(addedUserListToresponseString, logInUser);

        // 만들어진 body로 응답 객체를 만들어서 리턴
        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.OK, httpVersion))
                .set200Header(contentType, responseBody)
                .build();
    }

    private static StringBuilder makeHtml() {
        int index = 1;
        Collection<User> userList = Database.findAll();
        StringBuilder stringBuilder = new StringBuilder();

        for (User user : userList) {
            stringBuilder.append("<tr><th scope=\"row\">").append(index).append("</th>")
                    .append("<td>").append(user.getUserId()).append("</td>")
                    .append("<td>").append(user.getName()).append("</td>")
                    .append("<td>").append(user.getEmail()).append("</td>")
                    .append("<td><a class=\"btn btn-success\" href=\"#\" role=\"button\">수정</a></td></tr>");
            index++;
        }
        return stringBuilder;
    }

}
