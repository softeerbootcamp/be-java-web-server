package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.fileReader.TemplatesFileReader;
import request.HttpRequest;
import util.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private DataOutputStream clientOutputStream;
    private byte[] data;

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    public HttpResponse(DataOutputStream clientOutputStream,byte[] data) {
        this.clientOutputStream = clientOutputStream;
        this.data = data;
    }

    public void makeResponse(HttpRequest httpRequest) {
        HttpStatus responseHttpStatus = selectMethod(this);
        responseHeader(responseHttpStatus, httpRequest);
        responseBody();
    }

    private HttpStatus selectMethod(HttpResponse httpResponse) {
        if(httpResponse.getData().length==0){
            return HttpStatus.NOT_FOUND;
        }else{
            return HttpStatus.OK;
        }
    }

    private void responseHeader(HttpStatus httpStatus,HttpRequest httpRequest) {

        try {
            clientOutputStream.writeBytes("HTTP/1.1 " + httpStatus.getCode() + " " + httpStatus.getMessage() + " \r\n");
//            clientOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            clientOutputStream.writeBytes("Content-Type: " + httpRequest.getHeaderContents().get(9) + ";charset=utf-8\r\n");
            clientOutputStream.writeBytes("Content-Length: " + data.length + "\r\n");
            clientOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody() {
        try {
            clientOutputStream.write(data, 0, data.length);
            clientOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public byte[] getData() {
        return data;
    }


}
