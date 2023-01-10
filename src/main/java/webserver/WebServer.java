package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
	private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
	private static final int DEFAULT_PORT = 8080;

	public static void main(String args[]) throws Exception {
		int port = 0;
		if (args == null || args.length == 0) {
			port = DEFAULT_PORT;
		} else {
			port = Integer.parseInt(args[0]);
		}
		//System.out.println("start");
		//ServerSocket soc = new ServerSocket(8080);
		//soc.accept();
		//System.out.println("finish");
		// 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
		// 자동으로 resource 정리 try with resource
		// try wit resources 자원해제
		try (ServerSocket listenSocket = new ServerSocket(port)) {
			logger.info("Web Application Server started {} port.", port);

			// 클라이언트가 연결될때까지 대기한다.
			/*
			 * 클라이언트가 접속할 때 까지 대기
			 */
			Socket connection;
			while ((connection = listenSocket.accept()) != null) {
				// main 스레드에서만 실행 (싱글스레드)
				// RequestHandler r = new RequestHandler(connection);
				// r.run();

				// 스레드 만드는 오버헤드 매우 큼, 스레드 풀 미리 만들어놓고 요청시마다 스레드를 가져옴
				Thread thread = new Thread(new RequestHandler(connection));
				System.out.println(thread.getId() + " 스레드 생성");
				thread.start(); // 스레드 객체가 만들어지고 run이 실행됨, 스레드는 start() 메서드로만 생성됨
			}
		}
	}
}
