package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpStatus;
import util.Session;
import view.RequestMessage;
import view.Response;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class LoginController implements Controller{
    private static LoginController loginController;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String RELATIVE_PATH = "./src/main/resources";
    private static final String STATIC = "/static";
    private static final String TEMPLATES = "/templates";

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
        body = dynamicIndexPage(body, requestMessage);
        Response response = new Response(new DataOutputStream(out));
        response.response(body,requestMessage.getRequestHeaderMessage(), HttpStatus.Success);
    }

    private byte[] dynamicIndexPage(byte[] body, RequestMessage requestMessage){
        return (body.toString()).replace("로그인", Session.loginSession.get(requestMessage.getRequestHeaderMessage().getSessionId())).getBytes();
    }

    private byte[] getStaticBody(RequestMessage requestMessage){
        if (requestMessage.getRequestHeaderMessage().getHttpOnlyURL().contains(".")) {
            return StaticController.getInstance().getResponseBody(requestMessage);
        }
        if (requestMessage.getRequestHeaderMessage().getHttpOnlyURL().startsWith("/user")) {
            Controller controller = UserController.getInstance();
            //todo: 로그인 후 user에 대한 조작 명령이 있을 때 여기서 처리
        }
        return new byte[0];
    }
}
