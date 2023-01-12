package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.StringParser;
import service.UserService;
import util.FileIoUtils;
import util.Utilities;


public class RequestHandler implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public StringParser stringParser = new StringParser();

    public UserService userService = new UserService();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {

        /*TODO ( step1 )
        *  해야할 것
         * 1. 브라우저에서 서버로부터 데이터를 받아들이는 부분을 구현해야 함
         * ex.
         * InputStream: 브라우저에서 서버쪽으로 들어오는 모든 데이터가 담겨있음
         * OutStream : 서버에서 클라이언트쪽으로 응답을 보낼 , 응답을 보내는 데이터를 싣어줌
         * InputStream -> BufferedReader 로 변환하는 api 필요
         * BufferedReader : 클라이언트에서 요청하는 데이터를 Line by Line으로 읽어들임
         * ex. BufferedReader br = new BufferedReader(new InputStream(inputStream객체,"UTF-8"));
         * 2. request 를 읽어들이는 방법
         * (= null) 로 마지막인지 테스트하는 것이 아닌, (= !line.equals("")) 로 비교한다.
         * 3. sout 보단 로거 활용해서 출력 확인
         * logger 를 활용하면 어느 쓰레드에서 로그를 찍는지도 볼 수 있다.
         * [ 문제 해결 방법 ]
         * 첫 번째 라인에 해당하는 line 객체 부분에서
         * index.html 를 출력한 다음에
         * 서버에서 이 디렉토리 밑에 있는 index.html 파일을 읽어서
         * hello World 대신에 응답으로 쏴주면 첫번째 요구사항을 만족한다.
         */

        /* TODO (step2)
        *   HTTP GET 프로토콜을 이해한다.
        *   HTTP GET에서 parameter를 전달하고 처리하는 방법을 학습한다.
        *   HTTP 클라이언트에서 전달받은 값을 서버에서 처리하는 방법을 학습한다.
        * */

        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            DataOutputStream dos;

            byte[] body = {};

            String brRead = br.readLine();

            String[] urlString = stringParser.stringSplit(brRead);

            String url = urlString[1];

            boolean check = Utilities.checkData(url);

            while (!brRead.equals("") && brRead != null) brRead = br.readLine();

            if(!check){
                String userId = userService.joinUser(urlString[1]);
                logger.debug("userId : {}",userId);
            }

            body = FileIoUtils.loadFileFromClasspath("./templates" + url);
            dos = new DataOutputStream(out);

//            response302Header(dos, body.length);
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void response302Header(DataOutputStream dos,int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
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
