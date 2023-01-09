package webserver;

public class UserRequestHandler {

    public String handle(String url) {
        if (url.contains("/create")) {
            UserService userService = new UserService();
            userService.signUp(url);

            return "/user/login.html";
        }

        // 추후 다른 기능이 추가되면 수정할 예정
        return url;
    }
}
