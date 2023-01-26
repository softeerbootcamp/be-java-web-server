package repository;

import db.Database;
import db.UserDAO;
import model.User;
import model.request.UserCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class UserDAOTest {

    @BeforeEach
    void setUp() throws SQLException {
        UserDAO userDAO = new UserDAO();
        userDAO.deleteAll();
    }

    @Test
    @DisplayName("유저정보 저장 테스트")
    void userSave() throws Exception {
        //given
        UserCreate userCreate = new UserCreate("test", "123123", "tester", "test.com");

        //when
        UserDAO userDAO = new UserDAO();
        userDAO.insert(userCreate);

        //then
//        Assertions.assertThat();
    }

    @Test
    @DisplayName("xml 읽을 수 있는지")
    void xml() throws Exception {
        //given
        Database database = Database.getInstance();
        //when
    }

}
