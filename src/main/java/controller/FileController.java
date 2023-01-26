package controller;

import request.HttpRequest;
import response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.StatusCode;
import util.FileIoUtil;
import exception.*;
import view.FileView;

import java.util.Objects;

public class FileController implements Controller {
    public static final String PATH = "file";
    private final Logger logger = LoggerFactory.getLogger(FileController.class);
    private static FileController fileController = null;

    public static FileController getInstance() {
        if (Objects.isNull(fileController)) {
            synchronized (FileController.class) {
                fileController = new FileController();
            }
        }
        return fileController;
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        try {
            FileIoUtil.isExistFile(httpRequest.getPath());
        } catch (NotExistFileException e) {
            return response404(httpRequest);
        }
        return responseFile(httpRequest);
    }

    private HttpResponse responseFile(HttpRequest httpRequest) {
        byte[] body = FileView.getInstance().render(httpRequest);
        HttpResponse response = HttpResponse.createResponse(httpRequest.getPath(), StatusCode.OK, httpRequest.getProtocol());
        response.setHttpResponseBody(body);
        response.putHeader("Content-Length", String.valueOf(body.length));
        return response;

    }

    public HttpResponse response404(HttpRequest httpRequest) {
        logger.debug("[response404]");
        byte[] body = "404: 존재하지않는 파일입니다.".getBytes();
        HttpResponse httpResponse = HttpResponse.createResponse("", StatusCode.NOT_FOUND, httpRequest.getProtocol());
        httpResponse.setHttpResponseBody(body);
        httpResponse.putHeader("Content-Length", String.valueOf(body.length));

        return httpResponse;
    }
}
