package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {

    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength+2]; // body 길이
        br.read(body, 0, contentLength+2); // body 배열에 body의 0 ~ contentLenth 만큼 받아옴
        String res = String.copyValueOf(body);
        return res; // char -> String
    }
}
