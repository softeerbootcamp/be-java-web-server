package controller;

import request.Request;
import response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticController implements Controller{

    @Override
    public void selectedController(Request request, Response response) throws IOException {
        String requestURL =  request.getRequestLine().getURL();

        System.out.println("firstLine : "+ request.getRequestLine().getURL());
        byte[] body = Files.readAllBytes(new File("./src/main/resources/static"+requestURL).toPath());
        response.responseCssHeader(body.length);
        response.responseBody(body);
    }
}
