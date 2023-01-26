package controller;

import request.HttpRequest;
import request.StatusCode;
import response.HttpResponse;
import view.QnaShowView;

import java.util.Objects;

public class QnaShowController implements Controller {
    public static final String PATH = "/qna/show";
    private static QnaShowController qnaShowController = null;

    public static QnaShowController getInstance() {
        if (Objects.isNull(qnaShowController)) {
            synchronized (QnaShowController.class) {
                qnaShowController = new QnaShowController();
            }
        }
        return qnaShowController;
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        byte[] body = QnaShowView.getInstance().render(httpRequest);
        HttpResponse response = HttpResponse.createResponse("/qna/show.html", StatusCode.OK, httpRequest.getProtocol());
        response.setHttpResponseBody(body);
        response.putHeader("Content-Length", String.valueOf(body.length));
        return response;
    }
}
