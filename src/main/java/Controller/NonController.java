package Controller;

import Request.HttpRequest;
import Response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class NonController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(NonController.class);
    public static final String INDEX_HTML = "/index.html";
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
            httpRequest.setPath(INDEX_HTML);
            return FileController.getInstance().createResponse(httpRequest);
        }
        return FileController.getInstance().response404(httpRequest);
    }
}
