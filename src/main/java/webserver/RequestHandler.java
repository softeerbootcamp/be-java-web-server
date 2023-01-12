package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.RequestMappingHandler;
import request.HttpRequest;
import request.HttpRequestUtils;
import response.HttpResponse;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());
		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			//DataOutputStream dos = new DataOutputStream(out);
			// HttpRequest HttpResponse

			HttpRequest httpRequest = HttpRequestUtils.httpRequestParse(in);
			Controller controller = RequestMappingHandler.findController(httpRequest);
			HttpResponse httpResponse = new HttpResponse();
			controller.service(httpRequest, httpResponse);
			logger.debug(httpRequest.getUrl().getPath());
			// 전송
			out.write(httpResponse.toString().getBytes(StandardCharsets.UTF_8));
			out.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}

}
