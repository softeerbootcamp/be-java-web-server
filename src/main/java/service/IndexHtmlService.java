package service;

import http.HttpStatus;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import model.User;
import util.HttpResponseUtils;

public class IndexHtmlService {
    public static HttpResponse service(String filePath, String uri, User loginUser, String httpVersion, String contentType) {

        // 파일 경로를 넘겨서 http response string 생성
        String responseString = new String(HttpResponseUtils.makeBody(uri, filePath));
        byte[] responseBody = responseString.replace("로그인", loginUser.getName()).getBytes();

        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.OK, httpVersion))
                .set200Header(contentType, responseBody)
                .build();
    }
}
