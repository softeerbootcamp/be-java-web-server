package response;

import request.Request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private final DataOutputStream outputStream;

    public static String DEFAULT_HTTP_VERSION = "HTTP/1.1";

    private HttpResponse(OutputStream outputStream) {
        this.outputStream = new DataOutputStream(outputStream);
    }

    // TODO: 응답 기능 요청 메소드 및 url로 분리하기
    public static void handleHttpResponse(OutputStream outputStream, Request request, Response response) throws IOException {
        HttpResponse httpResponse = new HttpResponse(outputStream);
        int contentLength = (response.getFile() == null ? response.getData().getBytes().length : response.getFile().length);
        httpResponse.outputStream.writeBytes(DEFAULT_HTTP_VERSION
                + " " + response.getCode());
        httpResponse.outputStream.writeBytes("Content-Type: " + request.getResourceFileContentType() + ";charset=utf-8\r\n");
        httpResponse.outputStream.writeBytes("Content-Length: " + contentLength + "\r\n");
        if(response.getCode() == HttpResponseStatus.FOUND.getCode()) {
            httpResponse.outputStream.writeBytes("Location: /index.html");
        }
        httpResponse.outputStream.writeBytes("\r\n");

        httpResponse.outputStream.write((response.getFile() == null ? response.getData().getBytes() : response.getFile()), 0, contentLength);
        httpResponse.outputStream.flush();
    }
}
