package controller;

import request.HttpRequest;
import response.HttpResponse;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URISyntaxException;

abstract class AbstractController implements Controller {

    @Override
    public final HttpResponse service(HttpRequest httpRequest) throws IOException, URISyntaxException, AuthenticationException {
        if (httpRequest.isGet()) {
            return get(httpRequest);
        }

        if (httpRequest.isPost()) {
            return post(httpRequest);
        }
        throw new UnsupportedOperationException("지원하지 않는 Http 요청 메서드입니다.");

    }

    public HttpResponse get(HttpRequest httpRequest) throws IOException, URISyntaxException {
        throw new RuntimeException("get method error");
    }

    public HttpResponse post(HttpRequest httpRequest) throws IOException, URISyntaxException, AuthenticationException {
        throw new RuntimeException("post method error");
    }
}
