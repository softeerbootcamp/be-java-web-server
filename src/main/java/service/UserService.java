package service;

import db.UserRepository;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class UserService {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(Map<String, String> parameters) {
        User user = new User(
                parameters.get(USER_ID),
                parameters.get(PASSWORD),
                parameters.get(NAME),
                parameters.get(EMAIL)
        );
        userRepository.addUser(user);
    }

    public void validateUser(String requestUserId, String requestPassword) {
        User user = userRepository.findUserById(requestUserId);
        if (user == null || !requestPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("로그인 실패");
        }
    }

    public byte[] makeUserListBody(String filepath) throws IOException {

        String fileData = new String(FileIoUtils.loadFile(filepath));

        fileData = fileData.replace("<!--userlist-->", userListToString());

        return fileData.getBytes();
    }

    private String userListToString() {
        Collection<User> userList = userRepository.findAll();
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

    public byte[] makeUserNameIndexBody(String id, String filepath) throws IOException {
        User user = userRepository.findUserById(id);
        logger.info(user.getUserId());

        String fileData = new String(FileIoUtils.loadFile(filepath));
        String target_index = "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>";
        String target_others = "<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>";
        return fileData
                .replace("<!--username-->", String.format("<li><a>%s</a></li>", user.getName()))
                .replace(target_index, "")
                .replace(target_others, "")
                .getBytes();
    }

}
