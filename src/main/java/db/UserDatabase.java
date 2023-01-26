package db;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.*;

public class UserDatabase {
    private static final Logger logger = LoggerFactory.getLogger(UserDatabase.class);

    private static final UserDatabase instance;

    private UserDatabase() {}

    static {
        instance = new UserDatabase();
    }

    public static UserDatabase getInstance() {
        return instance;
    }

    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO Users (id, pwd, userName, email) VALUES(?, ?, ?, ?)";
        String[] args = new String[4];
        args[0] = user.getId();
        args[1] = user.getPwd();
        args[2] = user.getUsername();
        args[3] = user.getEmail();

        QueryExecutor.executeUpdateQuery(query, args);
    }

    public Optional<User> findUserById(String userId) throws SQLException {
        String query = "SELECT * FROM Users WHERE id = ?";
        String[] args = new String[1];
        args[0] = userId;

        List<Map<String, String>> result = QueryExecutor.executeSelectQuery(query, args);

        if(result.size() == 0) {
            return Optional.empty();
        }

        String id = result.get(0).get("id");
        String pwd = result.get(0).get("pwd");
        String userName = result.get(0).get("userName");
        String email = result.get(0).get("email");

        return Optional.of(User.of(id, pwd, userName, email));
    }

    public Optional<User> findUserByIdAndPwd(String userId, String pwd) throws SQLException {
        String query = "SELECT * FROM Users WHERE id = ? AND pwd = ?";
        String[] args = new String[2];
        args[0] = userId;
        args[1] = pwd;

        List<Map<String, String>> result = QueryExecutor.executeSelectQuery(query, args);

        if(result.size() == 0) {
            return Optional.empty();
        }

        String id = result.get(0).get("id");
        String password = result.get(0).get("pwd");
        String username = result.get(0).get("username");
        String email = result.get(0).get("email");

        return Optional.of(User.of(id, password, username, email));
    }

    public Collection<User> findAll() throws SQLException {
        String query = "SELECT * FROM Users";
        String[] args = new String[0];

        List<Map<String, String>> result = QueryExecutor.executeSelectQuery(query, args);
        List<User> userList = new ArrayList<>();

        for(Map<String, String> res : result) {
            String id = res.get("id");
            String pwd = res.get("pwd");
            String username = res.get("username");
            String email = res.get("email");

            userList.add(User.of(id, pwd, username, email));
        }

        return userList;
    }
}
