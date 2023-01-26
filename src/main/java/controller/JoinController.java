package controller;

import request.HttpRequest;
import response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.UserDbUtil;
import request.StatusCode;

import java.util.Objects;


public class JoinController implements Controller {
    public static final String PATH = "/user/create";
    public static final String INDEX_HTML = "/index";
    public static final String USER_FORM_HTML = "/user/form.html";

    private final Logger logger = LoggerFactory.getLogger(JoinController.class);
    private static JoinController joinController = null;

    public static JoinController getInstance() {
        if (Objects.isNull(joinController)) {
            synchronized (JoinController.class) {
                joinController = new JoinController();
            }
        }
        return joinController;
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        logger.debug("select joinController");
        if (UserDbUtil.alreadyExist(httpRequest)) {
            return responseUserForm(httpRequest);
        }
        UserDbUtil.saveUser(httpRequest);
        return redirect(INDEX_HTML, httpRequest);
    }

    private HttpResponse responseUserForm(HttpRequest httpRequest) {
        HttpResponse httpResponse = HttpResponse.createResponse(USER_FORM_HTML, StatusCode.UNAUTHORIZED, httpRequest.getProtocol());
        return httpResponse;
    }

}
