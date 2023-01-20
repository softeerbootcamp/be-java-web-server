package controller;

import db.Database;
import request.HttpRequest;
import response.HttpResponse;
import webserver.TemplateFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserLoginListController extends AbstractController{
    @Override
    public HttpResponse get(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if ("true".equals(httpRequest.getCookieValue("logined"))) {
            return HttpResponse.ok()
                    .body(TemplateFactory.of("/user/list", Database.findAll()))
                    .build();
        }
        return HttpResponse.ok()
                .bodyByPath("./templates/user/login.html")
                .build();
    }
}
