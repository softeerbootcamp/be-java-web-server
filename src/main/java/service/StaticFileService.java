package service;

import http.HttpStatus;
import http.response.HttpResponse;
import http.response.HttpStatusLine;
import util.HttpResponseUtils;

public class StaticFileService {
    public static HttpResponse service(String filePath, String httpVersion, String contentType){
        // 파일 경로를 넘겨서 http response body 생성
        byte[] responseBody = HttpResponseUtils.makeBody(filePath);

        // 만들어진 body로 응답 객체를 만들어서 리턴
        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.OK, httpVersion))
                .set200Header(contentType, responseBody)
                .build();
    }
}
