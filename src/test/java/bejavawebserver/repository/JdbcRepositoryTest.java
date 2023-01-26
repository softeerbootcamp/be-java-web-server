package bejavawebserver.repository;

import bejavawebserver.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcRepositoryTest {
    @Autowired JdbcRepository jdbcRepository;
    @Test
    @DisplayName("데이터베이스에 유저 추가 테스트")
    void addUser() throws SQLException {
        // given
        User user = new User("testid", "testpassword", "주형", "test@gmail.com");
        // when
        jdbcRepository.addUser(user);
        // then
        assertThat(jdbcRepository.findUserById(user.getUserId())).usingRecursiveComparison().isEqualTo(user);
    }
}