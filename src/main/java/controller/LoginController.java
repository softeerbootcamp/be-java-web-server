package controller;

import model.domain.User;
import model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpStatus;
import util.Session;
import view.RequestMessage;
import view.Response;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LoginController implements Controller{
    private static LoginController loginController;
    private final UserService userService = UserService.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String RELATIVE_PATH = "./src/main/resources";

    private LoginController(){}

    public static LoginController getInstance(){
        if (loginController == null){
            synchronized (UserController.class){
                if (loginController == null){
                    loginController = new LoginController();
                }
            }
        }
        return loginController;
    }

    @Override
    public void control(RequestMessage requestMessage, OutputStream out) {
        byte[] body = getStaticBody(requestMessage);
        logger.info("body with static page:\n"+body.toString());
        if (requestMessage.getRequestHeaderMessage().getHttpOnlyURL().contains("html"))
            body = changeLoginToID(body, requestMessage);
        if (requestMessage.getRequestHeaderMessage().getHttpOnlyURL().startsWith("/user/list.html"))
            body = dynamicListPage(body, requestMessage);
        logger.info("body after dynamic change:\n"+body.toString());
        Response response = new Response(new DataOutputStream(out));
        response.response(body,requestMessage.getRequestHeaderMessage(), HttpStatus.Success);
    }

    private byte[] changeLoginToID(byte[] body, RequestMessage requestMessage){
        String userID = Session.loginSession.get(requestMessage.getRequestHeaderMessage().getSessionId());
        String bodyStr =  (new String(body)).replace("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>",
                "<li><a role=\"button\">"+ userID+"</a></li>");
        bodyStr = bodyStr.replace("<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>",
                "<li><a role=\"button\">"+ userID+"</a></li>");
        return bodyStr.getBytes();
    }

    private byte[] dynamicListPage(byte[] body, RequestMessage requestMessage){
        Collection<User> users = userService.findUsers();
        String bodyStr = new String(body);
        String bodyPrefix = bodyStr.substring(0,bodyStr.indexOf("<tbody>")+"<tbody>".length());
        String bodyPostfix = bodyStr.substring(bodyStr.indexOf("</tbody>"));
        StringBuilder result = new StringBuilder(bodyPrefix);
        for (User user: users){
            result.append("<tr>\n<th scope=\"row\">1</th> <td>")
                    .append(user.getUserId()).append("</td> <td>").append(user.getName()).append("</td> <td>")
                    .append(user.getEmail()).append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n</tr>\n");
        }
        result.append(bodyPostfix);
        return result.toString().getBytes();
    }

    private byte[] getStaticBody(RequestMessage requestMessage){
        if (requestMessage.getRequestHeaderMessage().getHttpOnlyURL().contains(".")) {
            String fileURL = RELATIVE_PATH + requestMessage.getRequestHeaderMessage().getSubPath() + requestMessage.getRequestHeaderMessage().getHttpOnlyURL();
            return getBodyFile(fileURL);
        }
        if (requestMessage.getRequestHeaderMessage().getHttpOnlyURL().startsWith("/user")) {
            //todo: 로그인 후 user에 대한 조작 명령이 있을 때 여기서 처리
        }
        return new byte[0];
    }
}
