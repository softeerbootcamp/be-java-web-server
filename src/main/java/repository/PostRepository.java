package repository;

import com.google.common.collect.Lists;
import dao.PostDAO;
import db.DBUtil;
import exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PostRepository {

    public List<PostDAO> getAllPosts() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Post";

        try {
            con = DBUtil.getConnection();
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();

            List<PostDAO> posts = Lists.newArrayList();

            while (rs.next()) {
                posts.add(getPostDAO(rs));
            }

            return posts;
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            close(con, pstm, rs);
        }
    }

    private PostDAO getPostDAO(ResultSet rs) throws SQLException {
        Long postId = rs.getLong("id");
        Long userId = rs.getLong("uid");
        String title = rs.getString("title");
        String content = rs.getString("content");
        Date createdDate = rs.getDate("createdDate");
        return PostDAO.of(postId, userId, title, content, createdDate);
    }

    private void close(Connection con, PreparedStatement pstm, ResultSet rs) {
        try {
            con.close();
        } catch (SQLException e) {
        }
        try {
            pstm.close();
        } catch (SQLException e) {
        }
        try {
            rs.close();
        } catch (SQLException e) {
        }
    }
}
