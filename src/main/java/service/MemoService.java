package service;

import db.MemoRepository;
import exception.CustomException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import http.session.HttpSession;
import http.session.SessionHandler;
import model.Memo;

import java.util.Objects;

public class MemoService {

    public HttpResponse create(HttpRequest httpRequest) {
        if (SessionHandler.validateSession(httpRequest.getSid())) {
            HttpSession httpSession = SessionHandler.getSession(httpRequest.getSid());
            String username = httpSession.getUserName();
            String content = httpRequest.getRequestBody().get("content");
            if(Objects.isNull(content)) {
                throw new CustomException("Memo cannot be empty.");
            }
            MemoRepository.addMemo(Memo.from(username, content));
            return HttpResponseFactory.FOUND("/index.html");
        }
        return HttpResponseFactory.FOUND("/user/login.html");
    }
}
