package controller;

import request.HttpRequest;
import request.StatusCode;
import response.HttpResponse;
import view.IndexView;

import java.util.Objects;

public class IndexController implements Controller {
    public static final String PATH = "/index";
    private static IndexController indexController = null;
    public static IndexController getInstance() {
        if (Objects.isNull(indexController)) {
            synchronized (IndexController.class) {
                indexController = new IndexController();
            }
        }
        return indexController;
    }
    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        byte[] body = IndexView.getInstance().render(httpRequest);
        HttpResponse response = HttpResponse.createResponse("/index.html", StatusCode.OK, httpRequest.getProtocol());
        response.setHttpResponseBody(body);
        response.putHeader("Content-Length", String.valueOf(body.length));
        return response;
    }
}
