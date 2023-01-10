package http.util;

import http.common.HttpBody;
import http.common.HttpHeaders;
import http.common.HttpStatus;
import http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseWriter {
    private HttpResponseWriter() {}

    private static final String HTTP_VERSION = "HTTP/1.1";

    public static void write(OutputStream out, HttpResponse response) {
        DataOutputStream dos = new DataOutputStream(out);
        writeStatus(dos, response.getStatus());
        writeHeaders(dos, response.getHeaders());
        writeBody(dos, response.getBody());
    }

    private static void writeStatus(DataOutputStream dos, HttpStatus status) {
        try {
            String statusLine = HTTP_VERSION + " " + status.code() + " " + status.name() + "\r\n";
            dos.writeBytes(statusLine);
        } catch (IOException e) {
            // TODO: INTERNAL_SERVER_ERROR 처리하기
            throw new RuntimeException("응답 상태 정보가 잘못되었습니다.");
        }
    }

    private static void writeHeaders(DataOutputStream dos, HttpHeaders headers) {
        try {
            for (String key: headers.keys()) {
                String header = key + " : " + headers.getValue(key) + "\r\n";
                dos.writeBytes(header);
            }
        } catch (IOException e) {
            // TODO: INTERNAL_SERVER_ERROR 처리하기
            throw new RuntimeException("응답 헤더 정보가 잘못되었습니다.");
        }
    }

    private static void writeBody(DataOutputStream dos, HttpBody body) {
        try {
            dos.writeBytes("\n");
            dos.write(body.data());
            dos.flush();
        } catch (IOException e) {
            // TODO: INTERNAL_SERVER_ERROR 처리하기
            throw new RuntimeException("응답 바디 정보가 잘못되었습니다.");
        }
    }
}
