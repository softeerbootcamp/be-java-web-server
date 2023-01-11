package service;

import db.Database;
import model.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserServiceTest {

    @Test
    void createUserTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "kgstiger");
        parameters.put("password", "123");
        parameters.put("name", "kim");
        parameters.put("email", "kgstiger@gmail.com");

        User user = new User(
                parameters.get("userId"),
                parameters.get("password"),
                parameters.get("name"),
                parameters.get("email")
        );

        UserService.create(parameters);
        assertThat(Database.findUserById("kgstiger").toString()).isEqualTo(user.toString());
    }

}
