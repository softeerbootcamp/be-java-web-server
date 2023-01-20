package webserver.service;

import webserver.Paths;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

public class InvalidAccesstoUserListService implements Service{
    public InvalidAccesstoUserListService(){}

    @Override
    public Response exec(Request req)
    {
        return new Response()
                .withVersion(req.getReqLine().getVersion())
                .withStatCodeAndText(302, "REDIR")
                .withHeaderKeyVal("Location", Paths.REDIR_TO_LOGIN);
    }
}
