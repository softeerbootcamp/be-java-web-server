package service;

import db.UserRepository;
import model.User;
import utils.FileIoUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class UserService {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    public void create(Map<String, String> parameters) {
        User user = new User(
                parameters.get(USER_ID),
                parameters.get(PASSWORD),
                parameters.get(NAME),
                parameters.get(EMAIL)
        );
        UserRepository.addUser(user);
    }

    public void validateUser(String requestUserId, String requestPassword) {
        User user = UserRepository.findUserById(requestUserId);
        if (user == null || !requestPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("로그인 실패");
        }
    }

    public byte[] makeUserListBody(String filepath) throws IOException {

        String fileData = new String(FileIoUtils.loadFile(filepath));

        fileData = fileData.replace("%user_list", userListToString());

        return fileData.getBytes();
    }

    private String userListToString() {
        Collection<User> userList = UserRepository.findAll();
        StringBuilder sb = new StringBuilder();

        int idx = 3;

        for (User user : userList) {
            sb.append("<tr>");
            sb.append("<th scope=\"row\">").append(idx).append("</th>");
            sb.append("<td>").append(user.getUserId()).append("</td>");
            sb.append("<td>").append(user.getName()).append("</td>");
            sb.append("<td>").append(user.getEmail()).append("</td>");
            sb.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
            sb.append("<tr>");
            idx++;
        }

        return sb.toString();
    }

}
