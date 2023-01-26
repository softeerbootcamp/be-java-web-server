package utils;


import model.Post;
import model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class FileUtils {
    private static final String STATIC_RESOURCE_PATH = FileUtils.class.getClassLoader().getResource("./static").getPath();
    private static final String TEMPLATE_RESOURCE_PATH = FileUtils.class.getClassLoader().getResource("./templates").getPath();
    private static final Set<String> TEMPLATE_EXTENSIONS = Set.of("html", "ico");

    public static byte[] loadFile(String file) throws IOException {
        String fileExtension = getExtension(file);
        if (TEMPLATE_EXTENSIONS.contains(fileExtension))
            return Files.readAllBytes(new File(TEMPLATE_RESOURCE_PATH + file).toPath());
        return Files.readAllBytes(new File(STATIC_RESOURCE_PATH + file).toPath());
    }

    public static boolean fileExists(String file) {
        String extension = getExtension(file);
        String path = TEMPLATE_EXTENSIONS.contains(extension) ? TEMPLATE_RESOURCE_PATH : STATIC_RESOURCE_PATH;
        File find = new File(path + file);
        return find.exists();
    }

    public static String getExtension(String file) {
        return file.substring(file.lastIndexOf(".") + 1);
    }
    public static byte[] createPage(String path, User user) throws IOException {
        String page = new String(loadFile(path));
        if (path.contains("index.html") && user != null) {
            page = page.replace("<!--username-->", String.format("<li><a>%s</a></li>", user.getName()));
        }
        return removeUselessButton(page, user != null).getBytes();
    }

    private static String removeLoginButton(String page) {
        String target_index = "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>";
        String target_others = "<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>";
        return page.replace(target_index, "").replace(target_others, "");
    }

    private static String removeLogoutButton(String page) {
        String target = "<li><a href=\"/user/logout\" role=\"button\">로그아웃</a></li>";
        return page.replace(target, "");
    }

    private static String removeUselessButton(String page, boolean loggedIn) {
        if (loggedIn)
            return removeLoginButton(page);
        return removePostButton(removeLogoutButton(page));
    }

    private static String removePostButton(String page) {
        String target = "<a href=\"./qna/form.html\" class=\"btn btn-primary pull-right\" role=\"button\">질문하기</a>";
        return page.replace(target, "");
    }

    public static byte[] createUserListPage(Collection<User> users, User loginUser) throws IOException {
        StringBuilder sb = new StringBuilder();
        String userListPage = removeUselessButton(new String(loadFile(PathManager.USER_LIST_PATH)), loginUser != null);
        sb.append(createLoginUserHTML(loginUser));
        int cnt = 2;
        for (User user : users) {
            if (user.getUserId().equals(loginUser.getUserId()))
                continue;
            sb.append("<tr>");
            sb.append(String.format("<th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td> <td>",
                    cnt++, user.getUserId(), user.getName(), user.getEmail()));
            sb.append("</td> </tr>");
        }
        return userListPage.replace("<!--userList-->", sb.toString()).getBytes();
    }

    public static String createLoginUserHTML(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        sb.append(String.format("<th scope=\"row\">1</th> <td>%s</td> <td>%s</td> <td>%s</td>",
                user.getUserId(), user.getName(), user.getEmail()));
        sb.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
        sb.append("</tr>");
        return sb.toString();
    }

    public static byte[] createPostListPage(User user, Collection<Post> posts) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Post post : posts) {
            sb.append("<li> <div class=\"wrap\"> <div class=\"main\">");
            sb.append("<strong class=\"subject\">  <a href=\"./qna/show.html\"> ");
            sb.append(post.getTitle());
            sb.append("</a> </strong>");
            sb.append("<div class=\"auth-info\"> <i class=\"icon-add-comment\"></i>");
            sb.append(String.format("<span class=\"time\">%s</span>", post.getDateTime()));
            sb.append(String.format("<a href=\"./user/profile.html\" class=\"author\">%s</a></div>", post.getWriter()));
            sb.append("</div> </div> </li>");
        }
        String homePage = new String(createPage(PathManager.HOME_PATH, user));
        return homePage.replace("<!--postList-->", sb.toString()).getBytes();
    }
}
