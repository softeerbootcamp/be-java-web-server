package webserver.service;

import db.Database;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AlreadyLoggedInService implements Service{
    private String sid_usrid;
    public AlreadyLoggedInService(String sid_usrid){
        this.sid_usrid = sid_usrid;
    }

    @Override
    public Response exec(Request req) throws IOException
    {
        String reqQuery = req.getReqLine().getQuery();
        String contentType = Files.probeContentType(new File(reqQuery).toPath());
        byte[] bodyByte = Service.urlToByte(reqQuery);
        String bodyString = new String(bodyByte);

        String userName = Database.findUserById(sid_usrid).getName();
        bodyString.replace(
                "<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>",
                "<li>" + userName + "</li>"
        );
        bodyByte = bodyString.getBytes();

        return new Response()
                .withVersion(req.getReqLine().getVersion())
                .withStatCodeAndText(200, "OK")
                .withHeaderKeyVal("Content-Type", contentType + ";charset=utf-8")
                .withHeaderKeyVal("Content-Length", Integer.toString(bodyByte.length))
                .withBodyBytes(bodyByte);
    }
}
