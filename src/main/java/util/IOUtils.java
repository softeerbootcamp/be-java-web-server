package util;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength]; // body 길이
        br.read(body, 0, contentLength); // body 배열에 body의 0 ~ contentLenth 만큼 받아옴
        return String.copyValueOf(body); // char -> String
    }
}
