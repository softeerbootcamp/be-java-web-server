package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.fileReader.TemplatesFileReader;
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

    public void makeResponse() {
        HttpStatus httpStatus = HttpStatus.selectMethod(this);
        responseHeader(httpStatus);
        responseBody();
    }

    private void responseHeader(HttpStatus httpStatus) {

        try {
            clientOutputStream.writeBytes("HTTP/1.1 " + httpStatus.getCode() + " " + httpStatus.getMessage() + " \r\n");
            clientOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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
