package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Url;
import util.Cookie;
import util.FileType;
import util.HttpStatus;

import java.io.IOException;

public class HttpResponse {
    private final Data data;

    private final FileType fileType;

    private final HttpStatus httpStatus;

    private final Url redirectUrl;
    private final Cookie cookie;

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    public static class Builder {
        private Data data;
        private FileType fileType;
        private HttpStatus httpStatus;
        private Cookie cookie;

        private Url redirectUrl;

        public Builder setData(Data data) {
            this.data = data;
            return this;
        }

        public Builder setFileType(FileType fileType) {
            this.fileType = fileType;
            return this;
        }

        public Builder setHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder setCookie(Cookie cookie) {
            this.cookie = cookie;
            return this;
        }

        public Builder setRedirectUrl(Url redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(data, fileType, httpStatus, cookie,redirectUrl);
        }
    }




    public HttpResponse(Data data,FileType fileType,HttpStatus httpStatus,Cookie cookie,Url redirectUrl) {
        this.data = data;
        this.fileType = fileType;
        this.httpStatus = httpStatus;
        this.cookie = cookie;
        this.redirectUrl = redirectUrl;
        responseHeader();
        responseBody();
    }



    private void responseHeader() {
        try {
            responseStatus();
            responseContentType();
            responseContentLength();
            if(cookie != null)
                responseCookie(cookie);
            if(redirectUrl != null)
                responseLocationIndexHtml(redirectUrl);
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

    private void responseLocationIndexHtml(Url redirectUrl) throws IOException {
        data.getClientOutputStream().writeBytes("Location: " + redirectUrl.getUrl() + " \r\n");
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
