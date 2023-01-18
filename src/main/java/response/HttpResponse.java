package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Cookie;
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
        responseHeader();
        responseBody();
    }

    public HttpResponse(Data data,FileType fileType,HttpStatus httpStatus,Cookie cookie) {
        this.data = data;
        this.fileType = fileType;
        this.httpStatus = httpStatus;
        responseHeader(cookie);
        responseBody();
    }



    private void responseHeader() {

        try {
            responseStatus();
            responseContentType();
            responseContentLength();
            responseLocationIndexHtml();
            data.getClientOutputStream().writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(Cookie cookie) {

        try {
            responseStatus();
            responseContentType();
            responseContentLength();
            responseLocationIndexHtml();
            responseCookie(cookie);
            data.getClientOutputStream().writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }



    private void responseStatus() throws IOException {
        data.getClientOutputStream().writeBytes("HTTP/1.1 " + httpStatus.getCode() + " " + httpStatus.getMessage() + " \r\n");
    }

    private void responseContentType() throws IOException {
        data.getClientOutputStream().writeBytes("Content-Type: "+fileType.getType() + ";charset=utf-8\r\n");
    }
    private void responseContentLength() throws IOException {
        data.getClientOutputStream().writeBytes("Content-Length: " + data.getData().length + "\r\n");
    }

    private void responseLocationIndexHtml() throws IOException {
        data.getClientOutputStream().writeBytes("Location: " + "/index.html" + " \r\n");
    }

    private void responseCookie(Cookie cookie) throws IOException {
        data.getClientOutputStream().writeBytes("Set-Cookie: "+cookie.getKey()+"="+cookie.getValue()+"; Path=/"+ " \r\n");
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
