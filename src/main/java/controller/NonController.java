package controller;

import request.HttpRequest;
import response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class NonController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(NonController.class);
    public static final String INDEX_HTML = "/index";
    private static NonController nonController = null;

    public static NonController getInstance() {
        if (Objects.isNull(nonController)) {
            synchronized (NonController.class) {
                nonController = new NonController();
            }
        }
        return nonController;
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        if (httpRequest.getPath().equals("/")) {
            return redirect(INDEX_HTML, httpRequest);
        }
        return FileController.getInstance().response404(httpRequest);
    }
}
