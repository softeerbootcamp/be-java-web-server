package service;

import db.Database;
import http.HttpStatus;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import model.User;
import util.HttpResponseUtils;

import java.util.Collection;

public class ListService {
    public static HttpResponse service(boolean isLogin, String fileNameExtension, String uri, String httpVersion, String contentType){
        // Login check
        if(isLogin) return logInListService(fileNameExtension, uri, httpVersion, contentType);

        // not Login
        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.FOUND, httpVersion))
                .setDestination("/user/login.html")
                .makeHeader()
                .build();
    }

    private static HttpResponse logInListService(String fileNameExtension, String uri, String httpVersion, String contentType){
        StringBuilder stringBuilder = makeHtml();
        String filePath = HttpResponseUtils.makeFilePath(fileNameExtension);

        // 파일 경로를 넘겨서 http response string 생성
        String responseString = new String(HttpResponseUtils.makeBody(uri, filePath));
        byte[] responseBody = responseString.replace("%userList%", stringBuilder.toString()).getBytes();

        // 만들어진 body로 응답 객체를 만들어서 리턴
        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.OK, httpVersion))
                .setBody(responseBody)
                .setContentType(contentType)
                .makeHeader()
                .build();
    }

    private static StringBuilder makeHtml() {
        int index = 3; // 이미 두개 들어 있음
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
