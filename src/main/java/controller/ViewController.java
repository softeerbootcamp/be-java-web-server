package controller;

import model.general.ContentType;
import model.general.Header;
import model.general.Status;
import model.request.Request;
import model.request.RequestLine;
import model.response.Response;
import model.response.StatusLine;
import model.session.Sessions;
import util.HeaderUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

import static model.general.ContentType.HTML;
import static model.general.ContentType.ICO;

public class ViewController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    private static final String templatePath = "./src/main/resources/templates";
    private static final String staticPath = "./src/main/resources/static";

    @Override
    public Response getResponse(Request request) {
        RequestLine requestLine = request.getRequestLine();

        if (Objects.nonNull(requestLine.getContentType())) return getStaticFileResponse(request);

        return Response.from(StatusLine.of(requestLine.getHttpVersion(), Status.NOT_FOUND));
    }

    private Response getStaticFileResponse(Request request) {
        RequestLine requestLine = request.getRequestLine();

        byte[] body = makeResponseBody(Sessions.isExistSession(request.getSessionId()), request);

        Map<Header, String> headers = HeaderUtils.response200Header(requestLine.getContentType(), body.length);

        return Response.of(StatusLine.of(requestLine.getHttpVersion(), Status.OK), headers, body);
    }

    private String generatePath(ContentType contentType) {
        if (HTML.equals(contentType) || ICO.equals(contentType)) return templatePath;
        else return staticPath;
    }

    private byte[] makeResponseBody(boolean isLogin, Request request) {
        RequestLine requestLine = request.getRequestLine();
        byte[] body;

        if (isLogin && requestLine.getContentType().equals(HTML)) {
            try {
                body = Files.readAllBytes(new File(generatePath(requestLine.getContentType()) +
                        requestLine.getUri()).toPath());

                String originalIndexHtml = new String(body);
                String resultIndexHtml = originalIndexHtml.replace("로그인",
                        Sessions.getSession(request.getSessionId()).getSessionData().get("user"));
                resultIndexHtml = resultIndexHtml.replace("user/login.html", "user/profile.html");
                return resultIndexHtml.getBytes();
            } catch (IOException e) {
                return new byte[0];
            }
        }

        try {
            body = Files.readAllBytes(new File(generatePath(requestLine.getContentType()) +
                    requestLine.getUri()).toPath());
        } catch (IOException e) {
            return new byte[0];
        }

        return body;
    }
}
