package view;

import utils.FileIoUtils;
import webserver.ContentType;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class LoginFailView implements View{
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) {
        response.setStatus(HttpStatus.OK);
        response.setContentType(ContentType.HTML);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html");
        response.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
        response.setBody(body);//body에는 요청한 파일 내용이 들어감
    }
}
