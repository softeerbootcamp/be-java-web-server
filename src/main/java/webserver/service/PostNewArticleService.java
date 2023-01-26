package webserver.service;

import webserver.constants.InBody;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.io.IOException;

public class PostNewArticleService extends AlreadyLoggedInService{

    public PostNewArticleService(String sid_userid){
        super(sid_userid);
    }

    public Response exec(Request req) throws IOException {
        Response res = super.exec(req);

        postNewArticle();

        // redir
        byte bodyByte[] = Service.urlToByte("index.html");
        res.setResBody(bodyByte);
        return res;
    }

    private postNewArticle()
    {

    }
}
