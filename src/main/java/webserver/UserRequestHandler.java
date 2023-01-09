package webserver;

public class UserRequestHandler {

    private static final String HTML_FILE = ".html";

    public String handle(String url) {
        if (url.contains("/create")) {
            UserService userService = new UserService();
            return userService.signUp(url) + HTML_FILE;
        }

        // 추후 다른 기능이 추가되면 수정할 예정
        return url;
    }
}
