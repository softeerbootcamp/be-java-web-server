package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.UserController;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private final HttpRequestUtils parser;
	private final ResponseWriter responseWriter;
	private Socket connection;

	private UserController userController;

	public RequestHandler(Socket connectionSocket) {
		this.userController = new UserController();
		this.parser = new HttpRequestUtils();
		this.responseWriter = new ResponseWriter();
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());
		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			DataOutputStream dos = new DataOutputStream(out);

			// HttpRequest 파싱
			HttpRequest httpRequest = HttpRequestUtils.httpRequestParse(in);
			logger.info(httpRequest.toString());
			// TODO: 아래 부분을 인터페이스를 활용하여 확장성 높이고 코드 중복 없애야함, response 또한 새로 클래스 만들어서 적용할 예정
			// 정적 데이터 요청일 경우의 처리
			File file = new File("./webapp" + httpRequest.getUrl().getPath());
			logger.debug(httpRequest.getUrl().getPath());

			if (file.isFile()) {
				byte[] body = Files.readAllBytes(file.toPath());
				responseWriter.responseWrite(dos, body);
			}

			if (httpRequest.urlStartsWith("/create")) {
				responseWriter.responseWrite(dos, userController.doPost(httpRequest).getBytes("UTF-8"));
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}

}
