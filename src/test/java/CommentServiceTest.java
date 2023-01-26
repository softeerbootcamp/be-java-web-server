import dto.Comment;
import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import service.CommentService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.List;
import java.util.Map;

import static utility.CredentialsParser.getDBCredentials;

public class CommentServiceTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    private static Connection conn = null;

    @BeforeAll
    public static void makeConnection() throws SQLException {
        Map<String, String> credentials = getDBCredentials();
        conn = DriverManager.getConnection(credentials.get("url"));
        System.setOut(new PrintStream(outContent));
    }

    @Test
    @DisplayName("comments 코멘트 생성 테스트")
    public void insertUserIntoTable() throws SQLException {
        SoftAssertions softly = new SoftAssertions();

        PreparedStatement pstmt = null;
        User user = new User.UserBuilder()
                .setUserId("gildong").setPassword("1234")
                .setEmail("gildong@asdf.com").setName("Hong Gildong").build();

        CommentService.createComment("첫 글", "gildong", "Hong Gildong");

        List<Comment> comments = CommentService.getAll();

        System.out.println(comments);

        // softly.assertThat(comments).is("gildong Hong Gildong");
    }

    @AfterEach
    public void clearDatabase() {
        PreparedStatement pstmt = null;

        try {
            String sql = "TRUNCATE comments";
            pstmt = conn.prepareStatement(sql);

            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void endConnection() throws SQLException {
        if(conn != null)
            conn.close();
        System.setOut(originalOut);
    }
}
