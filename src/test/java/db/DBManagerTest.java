package db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;


class DBManagerTest {

    DBManager dbManager;

    @BeforeEach
    void setUp() {
        dbManager = DBManager.getInstance();
    }

    @Test
    @DisplayName("연결 및 해제시 null 이나 예외가 발생하지 않으면 정상")
    void getConnection() {
        //given, when , then
        assertThatCode(() -> {
            Connection connection = dbManager.getConnection(); // command option v
            dbManager.close(connection,null );
        }).doesNotThrowAnyException();

    }
}
