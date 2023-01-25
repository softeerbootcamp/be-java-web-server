package repository;

import model.Qna;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QnaRepository {
    private static final Logger logger = LoggerFactory.getLogger(QnaRepository.class);
    private static QnaRepository qnaRepository = null;

    public static QnaRepository getInstance() {
        if (Objects.isNull(qnaRepository)) {
            synchronized (QnaRepository.class) {
                qnaRepository = new QnaRepository();
            }
        }
        return qnaRepository;
    }

    public void saveQna(Qna qna, Connection conn) {
        String sql = "insert into Qna (name, subject, content, date) values(?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, qna.getName());
            ps.setString(2, qna.getSubject());
            ps.setString(3, qna.getContent());
            ps.setString(4, qna.getDate());
            int row = ps.executeUpdate();
            if (!(row > 0)) {
                throw new RuntimeException("save 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Qna findOneById(int id, Connection conn) {
        String sql = "select * from Qna where id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String name = rs.getString("name");
            String subject = rs.getString("subject");
            String content = rs.getString("content");
            String date = rs.getString("date");
            return new Qna(id, name, subject, content, date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Qna findOneByName(String name, Connection conn) throws SQLException {
        String sql = "select * from Qna where name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt("id");
        String subject = rs.getString("subject");
        String content = rs.getString("content");
        String date = rs.getString("date");
        return new Qna(id, name, subject, content, date);
    }
    public List<Qna> find10Qna(Connection conn) {
        List<Qna> qnas = new ArrayList<>();
        String sql = "select * from Qna order by date desc limit 10";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String subject = rs.getString("subject");
                String content = rs.getString("content");
                String date = rs.getString("date");
                qnas.add(new Qna(id, name, subject, content, date));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        qnas.stream().forEach(q -> logger.debug(q.toString()));
        return qnas;
    }
}
