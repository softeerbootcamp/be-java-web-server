package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Request.StatusCode;
import util.FileIoUtil;
import util.HtmlBuildUtil;
import util.HttpResponseUtil;
import util.LoginUtil;
import Exception.*;
import view.FileView;

import java.util.Objects;

public class FileController implements Controller {
    public static final String PATH = "";
    private final Logger logger = LoggerFactory.getLogger(FileController.class);
    private static FileController fileController=null;

    public static FileController getInstance(){
        if(Objects.isNull(fileController)){
            fileController = new FileController();
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
        byte[] body = FileView.getInstance().render(httpRequest);
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders(httpRequest.getPath(), body.length))
                .body(new HttpResponseBody(body));
        return httpResponse;
    }

    public HttpResponse response404(HttpRequest httpRequest) {
        logger.debug("[response404]");
        byte[] body = "404: 존재하지않는 파일입니다.".getBytes();
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.NOT_FOUND, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders("", body.length))
                .body(new HttpResponseBody(body));
        return httpResponse;
    }
}
