package service;

import http.HttpStatus;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import model.User;
import util.HttpResponseUtils;

public class IndexHtmlService {
    public static HttpResponse service(String filePath, User logInUser, String httpVersion, String contentType) {

        // 파일 경로를 넘겨서 http response string 생성
        String responseString = new String(HttpResponseUtils.makeBody(filePath));
        byte[] responseBody = banLogInAndSignUpWhenUserLogInState(responseString, logInUser);

        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.OK, httpVersion))
                .set200Header(contentType, responseBody)
                .build();
    }

    public static byte[] banLogInAndSignUpWhenUserLogInState(String responseString, User logInUser){
        return responseString.replace(
                "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>\n" +
                        "                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>",
                "<li><a role=\"button\">" + logInUser.getName() + "</a></li>").getBytes();
    }
}
