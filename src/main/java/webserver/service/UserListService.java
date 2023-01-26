package webserver.service;

import db.mysql.DB_Users;
import model.User;
import webserver.constants.InBody;
import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.io.IOException;
import java.sql.SQLException;

public class UserListService extends AlreadyLoggedInService{

    private String sid_userid;

    public UserListService(String sid_usrid) {
        super(sid_usrid);
        this.sid_userid = sid_usrid;
    }

    @Override
    public Response exec(Request req) throws IOException
    {
        Response res = super.exec(req);

        byte[] bodyByte = res.getResBody();

        String bodyStr = new String(bodyByte);
        bodyStr = bodyStr.replace(InBody.beforeReplaced_UserList, generateAllUserList());

        return res.
                withBodyString(bodyStr);
    }

    private String generateAllUserList()
    {
        StringBuffer sb = new StringBuffer();
        int idx = 0;
        try{
            for(User user : DB_Users.findAll())
            {
            idx++;
            sb.append("<tr>")
                    .append("<th scope=\"row\">"+idx+"</th>")
                    .append("<td>"+user.getUserId()+"</td>")
                    .append("<td>"+user.getName()+"</td>")
                    .append("<td>"+user.getEmail()+"</td>")
                    .append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>")
                    .append("</tr>");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return sb.toString();
    }
}
