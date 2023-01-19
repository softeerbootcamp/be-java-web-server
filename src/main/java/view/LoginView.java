package view;

import utils.FileIoUtils;
import enums.ContentType;
import request.HttpRequest;
import response.HttpResponse;
import enums.HttpStatus;

public class LoginView implements View{
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) {
        response.setStatus(HttpStatus.OK);
        response.setContentType(ContentType.HTML);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");
        response.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
        response.setBody(body);//body에는 요청한 파일 내용이 들어감
    }
}
