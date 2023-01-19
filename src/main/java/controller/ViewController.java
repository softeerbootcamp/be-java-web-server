package controller;

import http.HttpSession;
import http.SessionHandler;
import http.request.HttpRequest;
import http.request.HttpUri;
import http.request.RequestHeader;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import http.response.HttpStatus;
import util.FileIoUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class ViewController implements Controller {

    @Override
    public HttpResponse doService(HttpRequest httpRequest) throws IOException {
        RequestLine requestLine = httpRequest.getRequestLine();
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        HttpUri httpUri = requestLine.getHttpUri();
        File file = FileIoUtil.getFile(requestLine.getHttpUri());
        byte[] body = Files.readAllBytes(file.toPath());

        if (httpUri.getPath().endsWith(".html") && SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (line.contains("로그인") || line.contains("회원가입")) {
                    continue;
                }
                if (line.contains("Posts")) {
                    sb.append("<li><a>").append(httpSession.getUserName()).append("</a></li>").append(System.lineSeparator());
                }
                sb.append(line).append(System.lineSeparator());
            }
            body = sb.toString().getBytes();
        }

        return HttpResponseFactory.OK(requestHeader.getContentType(), new HashMap<>(), body);
    }

    @Override
    public boolean isMatch(HttpRequest httpRequest) {
        return true;
    }
}
