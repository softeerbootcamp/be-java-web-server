package controller;

import request.HttpRequest;
import response.HttpResponse;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URISyntaxException;

abstract class AbstractController implements Controller {

    @Override
    public final HttpResponse service(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpRequest.isGet()) {
            return get(httpRequest);
        }

        if (httpRequest.isPost()) {
            return post(httpRequest);
        }
        throw new UnsupportedOperationException("지원하지 않는 Http 요청 메서드입니다.");
    }

    protected HttpResponse get(HttpRequest httpRequest) throws IOException, URISyntaxException {
        throw new UndefinedHttpRequestMethodException(httpRequest);
    }

    protected HttpResponse post(HttpRequest httpRequest) throws IOException, URISyntaxException {
        throw new UndefinedHttpRequestMethodException(httpRequest);
    }
}
