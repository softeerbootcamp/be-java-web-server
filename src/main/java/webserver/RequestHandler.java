package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utility;

/**
 * 해야할 것
 * 브라우저에서 서버로부터 데이터를 받아들이는 부분을 구현해야 함
 * ex.
 * InputStream: 브라우저에서 서버쪽으로 들어오는 모든 데이터가 담겨있음
 * OutStream : 서버에서 클라이언트쪽으로 응답을 보낼 , 응답을 보내는 데이터를 싣어줌
 * InputStream -> BufferedReader 로 변환하는 api 필요
 * BufferedReader : 클라이언트에서 요청하는 데이터를 Line by Line으로 읽어들임
 * ex. BufferedReader br = new BufferedReader(new InputStream(inputStream객체,"UTF-8"));
 *
 * request 를 읽어들이는 방법
 * = null 로 마지막인지 테스트하는 것이 아닌,
 * = !line.equals("") 로 비교한다.
 *
 * sout 보단 로거 활용해서 출력 확인
 * = logger 를 활용하면 어느 쓰레드에서 로그를 찍는지도 볼 수 있다.
 *
 * [ 문제 해결 방법 ]
 * 첫 번째 라인에 해당하는 line 객체 부분에서
 * index.html 를 출력한 다음에
 * 서버에서 이 디렉토리 밑에 있는 index.html 파일을 읽어서
 * hello World 대신에 응답으로 쏴주면 첫번째 요구사항을 만족한다.
 * **/

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public Utility utility = new Utility();
    Thread thread = new Thread();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.info("thread : {}", thread.getName());
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String brRead = br.readLine();
            logger.debug("request Line : {}",brRead);

            String urlString = utility.stringSplit(brRead);
            logger.debug("url: {} ", urlString);

            int extensionIdx = 0;

            for (int i = 0; i < urlString.length(); i++) {
                if(urlString.charAt(i) == '.'){
                    extensionIdx = i;
                }
            }

            String extensionString = urlString.substring(extensionIdx+1);

            logger.debug("exteionsionString : {}",extensionString);


            while (!brRead.equals("") && brRead != null) {
                logger.debug("header : {}", brRead);
                brRead = br.readLine();
            }

            DataOutputStream dos = new DataOutputStream(out);

            byte[] body = {};

            if(extensionString.equals("html")){
                body = Files.readAllBytes(new File("src/main/resources/templates" + urlString).toPath());
            }
            if(extensionString.equals("css")){
                logger.debug("full url : {}" , "src/main/resources/static" + urlString);
                logger.debug("css : " + extensionString);
                logger.debug("urlString : " + urlString);
                body = Files.readAllBytes(new File("src/main/resources" + urlString).toPath());

            }if(extensionString.equals("js")){
                body = Files.readAllBytes(new File("src/main/resources" + urlString).toPath());
            }

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            logger.debug("dos : {}");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
