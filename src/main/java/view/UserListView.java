package view;

import model.User;

import java.util.Collection;

public class UserListView {

    public static StringBuilder front() {
        return new StringBuilder().append("<!DOCTYPE html>\n" +
                "<html lang=\"kr\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>SLiPP Java Web Programming</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n" +
                "    <link href=\"../css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <!--[if lt IE 9]>\n" +
                "    <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n" +
                "    <![endif]-->\n" +
                "    <link href=\"../css/styles.css\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<nav class=\"navbar navbar-fixed-top header\">\n" +
                "    <div class=\"col-md-12\">\n" +
                "        <div class=\"navbar-header\">\n" +
                "\n" +
                "            <a href=\"../index.html\" class=\"navbar-brand\">SLiPP</a>\n" +
                "            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">\n" +
                "                <i class=\"glyphicon glyphicon-search\"></i>\n" +
                "            </button>\n" +
                "\n" +
                "        </div>\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">\n" +
                "            <form class=\"navbar-form pull-left\">\n" +
                "                <div class=\"input-group\" style=\"max-width:470px;\">\n" +
                "                    <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">\n" +
                "                    <div class=\"input-group-btn\">\n" +
                "                        <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </form>\n" +
                "            <ul class=\"nav navbar-nav navbar-right\">\n" +
                "                <li>\n" +
                "                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>\n" +
                "                    <ul class=\"dropdown-menu\">\n" +
                "                        <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>\n" +
                "                        <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>\n" +
                "                    </ul>\n" +
                "                </li>\n" +
                "                <li><a href=\"../user/list\"><i class=\"glyphicon glyphicon-user\"></i></a></li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</nav>\n" +
                "<div class=\"navbar navbar-default\" id=\"subnav\">\n" +
                "    <div class=\"col-md-12\">\n" +
                "        <div class=\"navbar-header\">\n" +
                "            <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>\n" +
                "            <ul class=\"nav dropdown-menu\">\n" +
                "                <li><a href=\"../user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>\n" +
                "                <li class=\"nav-divider\"></li>\n" +
                "                <li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>\n" +
                "            </ul>\n" +
                "            \n" +
                "            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">\n" +
                "            \t<span class=\"sr-only\">Toggle navigation</span>\n" +
                "            \t<span class=\"icon-bar\"></span>\n" +
                "            \t<span class=\"icon-bar\"></span>\n" +
                "            \t<span class=\"icon-bar\"></span>\n" +
                "            </button>            \n" +
                "        </div>\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">\n" +
                "            <ul class=\"nav navbar-nav navbar-right\">\n" +
                "                <li class=\"active\"><a href=\"../index.html\">Posts</a></li>\n" +
                "                <li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>\n" +
                "                <li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>\n" +
                "                <li><a href=\"#\" role=\"button\">로그아웃</a></li>\n" +
                "                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"container\" id=\"main\">\n" +
                "   <div class=\"col-md-10 col-md-offset-1\">\n" +
                "      <div class=\"panel panel-default\">\n" +
                "          <table class=\"table table-hover\">\n" +
                "              <thead>\n" +
                "                <tr>\n" +
                "                    <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>\n" +
                "                </tr>\n" +
                "              </thead>\n" +
                "              <tbody>\n"
        );
    }

    public static StringBuilder render(Collection<User> users) {
        StringBuilder sb = new StringBuilder();
        int idx = 1;

        for (User user : users) {
            sb.append("<tr>\n");
            sb.append(String.format("<th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>", idx++, user.getUserId(), user.getName(), user.getEmail()));
            sb.append("</tr>\n");
        }

        return sb;
    }

    public static StringBuilder back() {
        return new StringBuilder().append("</tbody>\n" +
                "          </table>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "<!-- script references -->\n" +
                "<script src=\"../js/jquery-2.2.0.min.js\"></script>\n" +
                "<script src=\"../js/bootstrap.min.js\"></script>\n" +
                "<script src=\"../js/scripts.js\"></script>\n" +
                "\t</body>\n" +
                "</html>"
        );
    }
}
