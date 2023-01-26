package controller;

import model.domain.User;
import model.general.Header;
import model.general.Method;
import model.general.Status;
import model.request.Request;
import model.request.RequestLine;
import model.response.Response;
import model.response.StatusLine;
import model.session.Sessions;
import service.MemoService;
import util.HeaderUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MemoController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @Override
    public Response getResponse(Request request) {
        RequestLine requestLine = request.getRequestLine();
        Method requestMethod = requestLine.getMethod();
        String requestUri = requestLine.getUri();

        if (requestMethod.equals(Method.POST) &&
                requestUri.startsWith("/memo")) return createMemoResponse(request);

        return Response.from(StatusLine.of(requestLine.getHttpVersion(), Status.NOT_FOUND));
    }

    private Response createMemoResponse(Request request) {
        Map<String, String> memoInfo = request.getBody().getContent();
        Map<Header, String> headers;
        RequestLine requestLine = request.getRequestLine();
        String sessionId = request.getSessionId();

        if (Sessions.isExistSession(sessionId)) {
            User user = (User) Sessions.getSession(sessionId).getSessionData().get("user");
            memoService.writeMemo(user, memoInfo);
        }

        headers = HeaderUtils.responseRedirectIndexHtmlHeader();
        return Response.of(StatusLine.of(requestLine.getHttpVersion(), Status.FOUND), headers);
    }
}
