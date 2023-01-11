package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class ResponseWriter {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-type";
    private static final String SET_COOKIE_HEADER_KEY = "Set-Cookie";
    private static final String LINE_DELIMITER = "\r\n";
    DataOutputStream dos;

    public ResponseWriter(OutputStream out){
        this.dos =  new DataOutputStream(out);
    }

    public void write(HttpRequest request, HttpResponse response) throws IOException {
        writeHeader(response);
        dos.writeBytes(LINE_DELIMITER); // HTTP HEADER와 Message body 사이 빈 줄
        writeBody(response);
    }

    private void writeHeader(HttpResponse response) throws IOException {
        dos.writeBytes(String.format("%s %d %s%s", HTTP_VERSION, response.getStatus().getCode(), response.getStatus(), LINE_DELIMITER)); //status line
        writeContentType(response);//contentType
        response.getHeaderKeys() // 그 외 헤더 부분 쓰기
                .forEach(k -> writeHeaderLine(k, response.getHeaderByKey(k)));
    }

    private void writeContentType(HttpResponse response) {
        if (response.getContentType() != null) {
            writeHeaderLine(CONTENT_TYPE_HEADER_KEY, response.getContentType().getHeaderValue());
        }
    }

    private void writeHeaderLine(String headerKey, String headerValue) {
        try {
            dos.writeBytes(String.format("%s: %s%s", headerKey, headerValue, LINE_DELIMITER));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void writeBody(HttpResponse response) throws IOException {
        if (response.getBody() != null) {
            //write( byte[] b, int off, int len ) : b[off] 부터 len 개의 바이트를 출력 스트림으로 보냅니다.
            dos.write(response.getBody(), 0, response.getBody().length); ////현재 버퍼에 저장되어 있는 내용들을 클라이언트에 보냄
            dos.flush(); // 이제 강제로 버퍼를 비움
        }
    }


}
