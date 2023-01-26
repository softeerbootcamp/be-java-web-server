package controller;

import http.request.HttpRequest;
import http.request.HttpUri;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MemoService;

import java.util.Arrays;

public class MemoController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);
    private static final MemoService memoService = new MemoService();

    @Override
    public HttpResponse doService(HttpRequest httpRequest) {
        HttpUri httpUri = httpRequest.getUri();
        try {
            return (HttpResponse) Arrays.stream(MemoService.class.getMethods())
                    .filter(m -> httpUri.getPath().contains(m.getName()))
                    .findFirst()
                    .get()
                    .invoke(memoService, httpRequest);
        } catch (Exception e) {
            logger.error(e.getCause().getMessage());
            return HttpResponseFactory.NOT_FOUND(e.getCause().getMessage());
        }
    }

}