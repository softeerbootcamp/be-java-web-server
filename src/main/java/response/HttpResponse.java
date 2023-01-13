package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileType;
import util.HttpStatus;

import java.io.IOException;

public class HttpResponse {
    private final Data data;

    private final FileType fileType;

    private final HttpStatus httpStatus;

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    public HttpResponse(Data data,FileType fileType,HttpStatus httpStatus) {
        this.data = data;
        this.fileType = fileType;
        this.httpStatus = httpStatus;
        makeResponse();
    }

    private void makeResponse() {
        responseHeader(httpStatus);
        responseBody();

    }



    private void responseHeader(HttpStatus httpStatus) {
        try {
            data.getClientOutputStream().writeBytes("HTTP/1.1 " + httpStatus.getCode() + " " + httpStatus.getMessage() + " \r\n");
            data.getClientOutputStream().writeBytes("Content-Type: "+fileType.getType() + ";charset=utf-8\r\n");
            data.getClientOutputStream().writeBytes("Content-Length: " + data.getData().length + "\r\n");
            data.getClientOutputStream().writeBytes("Location: " + "/index.html" + " \r\n");
            data.getClientOutputStream().writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }





    private void responseBody() {
        try {
            data.getClientOutputStream().write(data.getData(), 0, data.getData().length);
            data.getClientOutputStream().flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public Data getData() {
        return data;
    }


}
