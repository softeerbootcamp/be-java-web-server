package webserver;

//종류가 다른 두 개의 소켓을 혼용함 - 두 개 같은 게 아님!
import java.net.ServerSocket;
import java.net.Socket;
//implementation 'ch.qos.logback:logback-classic:1.2.3' , logback 라이브러리 사용
//log4j에 문제가 있었음
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class); //@Logger
    private static final int DEFAULT_PORT = 8080;
    //메인 쓰레드
    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 1. 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        // 생성자 내부에 bind()가 있고, bind() 내부에 listen() 있음
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            // serversocket은 soccket고 다름 - closeable을 implements하므로 따로 close 안 해줘도 auto close
            //try-with-resource
            logger.info("Web Application Server started {} port.", port);

            Socket connection;//클라이언트 소켓과의 연결 객체
            //accept()가 호출되면 프로그램은 여기서 실행을 멈추고 클라이언트가 port번으로 연결할 때까지 무한 대기
            //2. 서버 소켓 listenSocket가 accpet를 호출하고 리턴을 해주면 소켓인 connection 객체가 생성됨
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection));
                //logger.debug("thread name: " + thread.getName());
                thread.start();
            }
        }
    }
}
