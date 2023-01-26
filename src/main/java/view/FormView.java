package view;

import enums.ContentType;
import enums.HttpStatus;
import request.HttpRequest;
import response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class FormView implements View{
    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) throws IOException, URISyntaxException {
        response.setStatus(HttpStatus.OK);
        response.setContentType(ContentType.HTML);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/qna/form.html");
        response.addToHeader("Content-Length", String.valueOf(body.length));
        response.setBody(body);//body에는 요청한 파일 내용이 들어감
    }
}
