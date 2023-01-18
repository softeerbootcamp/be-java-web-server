package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpStatus;
import view.RequestMessage;

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

    }

}
