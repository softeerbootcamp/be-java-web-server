package webserver.view;

import model.User;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.utils.StaticResourceFinder;
import java.io.IOException;

public class ProfileView implements View{

    private ProfileView(){}

    public static ProfileView getInstance(){
        return ProfileView.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final ProfileView INSTANCE = new ProfileView();
    }

    private String getProfileScript(Object userObj){     //make html <tbody> tag with userList
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"media-body\">\n");
        sb.append("<h4 class=\"media-heading\">%s</h4>\n");
        sb.append("</strong> <div class=\"auth-info\"> <i class=\"icon-add-comment\"></i>");
        sb.append("<p>\n <a href=\"#\" class=\"btn btn-xs btn-default\"><span class=\"glyphicon glyphicon-envelope\"></span>&nbsp;javajigi@slipp.net</a>\n </p>\n </div>");

        String rawStr = sb.toString();
        sb.setLength(0);

        User user = User.class.cast(userObj);
        return String.format(rawStr, user.getName(), user.getEmail());
    }


    @Override
    public void render(Request req, Response res, ModelAndView mv, String menuBar, String nameTag) throws IOException {
        String userTableScript = getProfileScript(mv.getViewModel().get("user"));
        StaticResourceFinder.staticFileResolver(mv.getViewPath())
                .ifPresent( fileAsBytes -> {
                    String fileContent = new String(fileAsBytes);
                    fileContent = fileContent.replace(menuBar, "")
                            .replace("<li id=\"username\"></li>", nameTag)
                            .replaceAll("(?is)<!-- profile start -->.+?<!-- profile end -->", userTableScript);
                    res.addHeaderAndBody(StatusCodes.OK, fileContent.getBytes(), StaticResourceFinder.getExtension(mv.getViewPath()));
                });
    }
}
