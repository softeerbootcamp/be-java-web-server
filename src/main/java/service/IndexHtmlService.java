package service;

import http.HttpStatus;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import model.User;
import util.HttpResponseUtils;

public class IndexHtmlService {
    public static HttpResponse service(String fileNameExtension, String uri, User loginUser, String httpVersion, String contentType) {
        String filePath = HttpResponseUtils.makeFilePath(fileNameExtension);

        // 파일 경로를 넘겨서 http response string 생성
        String responseString = new String(HttpResponseUtils.makeBody(uri, filePath));
        byte[] responseBody = responseString.replace("로그인", loginUser.getName()).getBytes();

        // 만들어진 body로 응답 객체를 만들어서 리턴
        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.OK, httpVersion))
                .setBody(responseBody)
                .setContentType(contentType)
                .makeHeader()
                .build();
    }
}
