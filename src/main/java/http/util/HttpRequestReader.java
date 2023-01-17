package http.util;

import http.exception.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestReader {
    private HttpRequestReader() {}

    public static String readRequestLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    public static String readHeader(BufferedReader br) throws IOException {
        String line;
        StringBuilder strOfHeaders = new StringBuilder();
        while (!(line = br.readLine()).equals("")) {
            strOfHeaders.append(line).append("\n");
        }
        return strOfHeaders.toString();
    }

    public static String readBody(BufferedReader br, int contentLength) throws IOException {
        char[] data = new char[contentLength];
        int read = br.read(data);
        if (read == -1) {
            throw new BadRequestException("요청 형식이 잘못되었습니다.");
        }
        return String.valueOf(data);
    }
}
