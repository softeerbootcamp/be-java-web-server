package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.FrontController;
import request.HttpRequest;
import request.HttpRequestUtils;
import response.HttpResponse;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private Socket connection;
	private FrontController frontController;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
		this.frontController = FrontController.getInstance();
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());
		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

			HttpRequest httpRequest = HttpRequestUtils.httpRequestParse(in);
			HttpResponse httpResponse = new HttpResponse();
			frontController.service(httpRequest, httpResponse);

			// 전송
			out.write(httpResponse.toString().getBytes(StandardCharsets.UTF_8));
			out.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}

}
