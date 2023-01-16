package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import response.ContentType;
import request.HttpRequest;
import response.HttpResponse;
import webserver.HttpStatus;

public class FileController extends AbstractController {
	private final static String RESOURCE_DIR = "/webapp";
	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		String path = httpRequest.getUrl().getPath();
		String[] splitList = path.split("\\.");
		final String extension = splitList[splitList.length - 1];
		File file = new File(RESOURCE_DIR + httpRequest.getUrl().getPath());
		httpResponse.setHttpResponse(HttpStatus.OK, new String(Files.readAllBytes(file.toPath())),
			ContentType.findContentType(extension));
	}
}
