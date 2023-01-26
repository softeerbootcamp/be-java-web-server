package repository;

import com.google.common.collect.Lists;
import dao.PostDAO;
import db.DBUtil;
import exception.DBException;
import model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PostRepository {

    public Post save(Post post) {
        Connection con = null;
        PreparedStatement pstm = null;
        String sql = "INSERT INTO Post(uid, title, content, createdDate) VALUES(?, ?, ?, ?)";

        try {
            con = DBUtil.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setLong(1, post.getUser().getId());
            pstm.setString(2, post.getTitle());
            pstm.setString(3, post.getContent());
            pstm.setString(4, post.getCreatedDate());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.close(con, pstm, null);
        }

        return post;
    }

    public List<PostDAO> getAllPosts() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<PostDAO> posts = Lists.newArrayList();
        String sql = "SELECT * FROM Post";
        try {
            con = DBUtil.getConnection();
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                posts.add(PostDAO.of(
                        rs.getLong("id"),
                        rs.getLong("uid"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("createdDate").toLocalDateTime()));
            }
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.close(con, pstm, rs);
            return posts;
        }
    }

    public Optional<PostDAO> findById(long id) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        PostDAO postDAO = null;
        String sql = "SELECT * FROM Post WHERE id = (?)";
        try {
            con = DBUtil.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                postDAO = PostDAO.of(
                        rs.getLong("id"),
                        rs.getLong("uid"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("createdDate").toLocalDateTime()
                );
            }
            throw new IllegalStateException("post not found");
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DBUtil.close(con, pstm, rs);
            return Optional.ofNullable(postDAO);
        }
    }
}
