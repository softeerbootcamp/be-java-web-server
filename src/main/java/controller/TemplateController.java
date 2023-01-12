package controller;

import enums.ContentTypeEnum;
import enums.ControllerTypeEnum;
import request.Request;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TemplateController implements Controller {
    @Override
    public void selectedController(Request request, Response response) throws IOException {
        System.out.println("firstLine : " + request.getRequestLine().getURL());
        String url = request.getRequestLine().getURL();

        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + url).toPath());
        response.responseMaker(ControllerTypeEnum.TEMPLATE, ContentTypeEnum.HTML, body.length, url);
        response.responseBody(body);
    }
}
