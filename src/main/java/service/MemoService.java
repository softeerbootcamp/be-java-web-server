package service;

import db.MemoRepository;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import http.session.HttpSession;
import http.session.SessionHandler;
import model.Memo;

public class MemoService {

    public HttpResponse create(HttpRequest httpRequest) {
        if (SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            String username = httpSession.getUserName();
            String content = httpRequest.getRequestBody().get("content");
            MemoRepository.addMemo(Memo.from(username, content));
        }
        return HttpResponseFactory.FOUND("/index.html");
    }
}
