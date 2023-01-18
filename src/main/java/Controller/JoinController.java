package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseUtil;
import util.UserDbUtil;
import Request.StatusCode;


public class JoinController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(JoinController.class);

    public JoinController() {
        logger.debug("select joinController");
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        if (UserDbUtil.alreadyExist(httpRequest)) {
            return responseUserForm(httpRequest);
        }
        UserDbUtil.saveUser(httpRequest);
        return redirect("/index.html", httpRequest);
    }

    private HttpResponse responseUserForm(HttpRequest httpRequest) {
        byte[] body = HttpResponseUtil.generateBody("/user/form.html");
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders("/user/form.html", body.length))
                .body(new HttpResponseBody(body));
        return response;
    }

}
