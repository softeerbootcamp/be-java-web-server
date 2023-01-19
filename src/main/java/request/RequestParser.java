package request;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParser {

    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_SPLIT_LIMIT = 2; //split된 배열의 크기

    public static HttpRequest parseInputStreamToHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        RequestStartLine startLine = RequestStartLine.from(br.readLine());
        Map<String,String> headers = parseHeader(br);
        String cookieHeader = headers.get("Cookie");
        Map<String,String> cookies = parseCookie(cookieHeader);
        String body = readBody(br,headers);
        return new HttpRequest(startLine.getMethod(),startLine.getUrl(),startLine.getQueries(), cookies, headers,body);

    }

    private static Map<String, String> parseHeader(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        line = br.readLine();
        while (hasMoreLine(line)) {
            String[] headerTokens = line.split(HEADER_DELIMITER, HEADER_SPLIT_LIMIT);
            headers.put(headerTokens[0], headerTokens[1]);
            line = br.readLine();
        }
        return headers;
    }

    private static Map<String,String> parseCookie(String cookieHeader){
        if (cookieHeader != null) {
            return Arrays.stream(cookieHeader.split("; "))
                    .collect(Collectors.toMap(
                            token -> token.split("=", 2)[0],
                            token -> token.split("=", 2)[1]));
        }
        return new HashMap<>();
    }

    private static boolean hasMoreLine(String line) {
        return !(line == null || line.isEmpty());
    }

    private static String readBody(BufferedReader br, Map<String, String> headers) throws IOException {
        String contentLengthHeader = headers.get("Content-Length");
        //contentLength가 있다 == body가 존재한다
        if (contentLengthHeader != null) {
            Integer contentLength = Integer.parseInt(contentLengthHeader);
            //int read(char[] cbuf, int off, int len) : 인수로 들어간 cbuf의 문자열에서, off(=offset) 부터 len만큼의 문자열을 읽습니다.
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            //System.out.println(String.copyValueOf(body));
            return String.copyValueOf(body);
        }
        return "";
    }

    public static String parseRequestStartLineTarget(InputStream in) throws IOException {
        //inputstream은 bufferedreader를 통해 line by line으로 읽을 수 있음
        BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        String line = br.readLine();
        if(line == null) return null; // 헤더가 null일 경우 null을 응답
        String[] headLine = line.split(" "); // 헤더의 첫 번째 줄을 공백을 기준으로 자른다
        String url = headLine[1];//index.html, css파일, js 파일 등,,,
        System.out.println(url);
        ///user/form.html
        //user/create?userId=user&password=66a41ad2-bda9-4c70-807c-e0e13ff04475&name=1234&email=1234%40khu.ac.kr

        return url;
    }

    public static void printRequestHeader(InputStream in) throws IOException {
         //header 전체를 출력하기
        BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        String line = br.readLine();
        if(line == null) return ;
        while(!line.equals(" ")){ // http 요청을 끝나는 지점은 null이 아니라 빈 공백 문자열임
            System.out.println("request: "+line);
            line = br.readLine();
        }
    }
}
