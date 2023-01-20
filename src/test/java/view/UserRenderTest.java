package view;

import db.UserDatabase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reader.fileReader.FileReader;
import reader.fileReader.TemplatesFileReader;
import request.RequestDataType;
import request.Url;

import java.io.IOException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRenderTest {
    final String[] userValue = {"test", "1234", "testName", "test@naver.com"};
    final String[] userValue2 = {"test2", "1234", "test2Name", "tes2t@naver.com"};
    final String[] userKey = {"userId", "password", "name", "email"};

    UserDatabase userDatabase = new UserDatabase();
    UserRender userRender = UserRender.getInstance();
    FileReader fileReader = new TemplatesFileReader();
    @Test
    @DisplayName("유저 리스트 랜더링 테스트")
    void addUserList() throws IOException {
        //given
        saveUsers();
        byte[] userListHtml = fileReader.readFile(new Url("/user/list.html", RequestDataType.FILE));
        Collection<User> users = userDatabase.findAll();
        //when
        byte[] fixedUserListHtml = userRender.addUserList(userListHtml, users);
        //then
        for (User user : users) {
            assertThat(new String(fixedUserListHtml).contains(user.getUserId())).isTrue();
            assertThat(new String(fixedUserListHtml).contains(user.getEmail())).isTrue();
            assertThat(new String(fixedUserListHtml).contains(user.getName())).isTrue();
        }
    }

    private void saveUsers() {
        userDatabase.addData(new User(userValue[0], userValue[1], userValue[2], userValue[3]));
        userDatabase.addData(new User(userValue2[0], userValue2[1], userValue2[2], userValue2[3]));
    }
}