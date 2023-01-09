package webserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private StringParser parser;
	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.parser = new StringParser();
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String url = parser.pathParse(br.readLine());
			String line = br.readLine();
			while (!line.equals("")) {
				System.out.println(line);
				line = br.readLine();
			}
			logger.debug("threadID : {} request path : {}", Thread.currentThread().getId(), url);
			byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
			DataOutputStream dos = new DataOutputStream(out);
			response200Header(dos, body.length);
			responseBody(dos, body);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
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
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
