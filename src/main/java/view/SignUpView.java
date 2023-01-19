package view;

import model.User;
import utils.FileIoUtils;
import webserver.*;

public class SignUpView implements View{
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) {
        response.setStatus(HttpStatus.OK);
        response.setContentType(ContentType.HTML);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
        response.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
        response.setBody(body);//body에는 요청한 파일 내용이 들어감
    }
}
