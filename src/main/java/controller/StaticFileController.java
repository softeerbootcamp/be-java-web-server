package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.HttpStatus;
import http.response.HttpStatusLine;
import util.HttpResponseUtils;

import java.io.IOException;

public class StaticFileController implements Controller {

    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) {
        // ContentType를 받음
        String contentType = httpRequest.getContentType();
        // 파일 확장자를 받음
        String fileNameExtension = httpRequest.getFileNameExtension();
        // 파일 확장자를 통해 파일 경로 설정
        // TODO 여러 확장자에 대한 처리 필요
        // 일단은 html 확장자일 경우 /templates 그 외는 /static
        String filePath = HttpResponseUtils.makeFilePath(fileNameExtension);

        // 파일 경로를 넘겨서 http response body 생성
        byte[] responseBody = HttpResponseUtils.makeBody(httpRequest.getUri(), filePath);

        // TODO 여러 HttpStatus에 대한 처리 필요
        // 일단은 200 OK 고정 박아놓음
        // 만들어진 body로 응답 객체를 만들어서 리턴
        return new HttpResponse.HttpResponseBuilder()
                .setHttpStatusLine(new HttpStatusLine(HttpStatus.OK, httpRequest.getHttpVersion()))
                .makeHeader()
                .setBody(responseBody)
                .setContentType(contentType)
                .build();
    }
}
