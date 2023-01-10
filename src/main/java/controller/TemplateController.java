package controller;

import request.Request;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TemplateController implements Controller{
    @Override
    public void selectedController(Request request, Response response) throws IOException {
        System.out.println("firstLine : "+ request.getRequestLine().getURL());
        String requestURL =  request.getRequestLine().getURL();

        byte[] body = Files.readAllBytes(new File("./src/main/resources/templates"+requestURL).toPath());
        response.response200Header(body.length);
        response.responseBody(body);
    }
}
