package controller;

import model.domain.Memo;
import model.domain.User;
import model.service.MemoService;
import model.service.UserService;
import util.*;
import view.RequestMessage;
import view.Response;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LoginController implements Controller{
    private static LoginController loginController;
    private final UserService userService = UserService.getInstance();
    private final MemoService memoService = MemoService.getInstance();
    private static final String RELATIVE_PATH = "./src/main/resources";

    private LoginController(){}

    public static LoginController getInstance(){
        if (loginController == null){
            synchronized (LoginController.class){
                if (loginController == null){
                    loginController = new LoginController();
                }
            }
        }
        return loginController;
    }

    @Override
    public void control(RequestMessage requestMessage, OutputStream out) {
        Map<String, String> headerKV = new HashMap<>();
        byte[] body = processRequest(requestMessage, headerKV);
        Response response = new Response(new DataOutputStream(out));
        response.response(body,requestMessage.getRequestHeaderMessage(), headerKV);
    }

    private byte[] changeNavbar(byte[] body, RequestMessage requestMessage){
        String userID = Session.loginSession.get(requestMessage.getRequestHeaderMessage().getSessionId());
        body = HtmlEditor.removeHref(body);
        body = HtmlEditor.editNavbar(body,userID);
        return body;
    }

    private byte[] dynamicMemoList(byte[] body, int count){
        Collection<Memo> memos = memoService.findRecentMemos(count);
        body = HtmlEditor.appendMemoList(body,memos);
        return body;
    }

    private byte[] dynamicListPage(byte[] body){
        Collection<User> users = userService.findUsers();
        return HtmlEditor.appendUserList(body,users);
    }

    private byte[] processRequest(RequestMessage requestMessage, Map<String,String> headerKV){
        String uri = requestMessage.getRequestHeaderMessage().getHttpOnlyURL();
        if (uri.contains(".")) {
            return processDynamicPage(requestMessage);
        }
        if (uri.startsWith("/user")) {
            //todo: 로그인 후 user에 대한 조작 명령이 있을 때 여기서 처리
        }
        if (uri.startsWith("/memo")){
            return processMemo(requestMessage, headerKV);
        }
        return new byte[0];
    }

    private byte[] processDynamicPage(RequestMessage requestMessage){
        String fileURL = RELATIVE_PATH + requestMessage.getRequestHeaderMessage().getSubPath() + requestMessage.getRequestHeaderMessage().getHttpOnlyURL();
        byte[] body = getBodyFile(fileURL);
        if (requestMessage.getRequestHeaderMessage().getHttpOnlyURL().contains("html")){
            body = changeNavbar(body, requestMessage);
            body = dynamicMemoList(body, 6);
        }
        if (requestMessage.getRequestHeaderMessage().getHttpOnlyURL().startsWith("/user/list.html"))
            body = dynamicListPage(body);
        return body;
    }

    private byte[] processMemo(RequestMessage requestMessage, Map<String,String> headerKV){
        String uri = requestMessage.getRequestHeaderMessage().getHttpOnlyURL();
        if (uri.contains("create")){
            createMemo(requestMessage, headerKV);
        }
        return new byte[0];
    }

    private void createMemo(RequestMessage requestMessage, Map<String,String> headerKV){
        Map<String,String> bodyMap = MessageParser.parseQueryString(requestMessage.getRequestBodyMessage().getBodyParams());
        Memo memo = new Memo(bodyMap.get("memo"),Session.loginSession.get(requestMessage.getRequestHeaderMessage().getSessionId()));
        memoService.post(memo);
        setLocation(Redirect.getRedirectLink(requestMessage.getRequestHeaderMessage().getRequestAttribute()),headerKV);
    }

}
