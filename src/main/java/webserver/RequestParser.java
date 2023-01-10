package webserver;

import java.io.*;

public class RequestParser {

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
