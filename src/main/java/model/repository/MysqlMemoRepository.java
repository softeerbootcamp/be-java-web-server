package model.repository;

import model.domain.Memo;
import model.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MysqlMemoRepository implements MemoRepository{

    private static MysqlMemoRepository mysqlMemoRepository;
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private static final String url = "jdbc:mysql://localhost:3306/jdbc";
    private static final String userName = "root";
    private static final String password = "vldb1234";

    private MysqlMemoRepository(){}

    public static MemoRepository getInstance(){
        if (mysqlMemoRepository == null){
            synchronized (MysqlMemoRepository.class){
                if (mysqlMemoRepository == null){
                    mysqlMemoRepository = new MysqlMemoRepository();
                }
            }
        }

        return mysqlMemoRepository;
    }

    @Override
    public Memo addMemo(Memo memo) {
        String sql = "insert into memo(memoId, content, userId) values(?, ?, ?)";
        try {
            conn = DriverManager.getConnection(url,userName,password);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memo.getMemoId());
            pstmt.setString(2, memo.getContent());
            pstmt.setString(3, memo.getUserId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            return memo;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Collection<Memo> findRecent(int count) {
        String sql = "select * from memos order by memoId desc limit ?";
        Collection<Memo> memos = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url,userName,password);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, count);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Memo memo = new Memo(rs.getInt("memoId"),rs.getString("content"),
                        rs.getString("userId"));
                memos.add(memo);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return memos;
        }
    }

    @Override
    public Collection<Memo> findAll() {
        String sql = "select * from memos";
        Collection<Memo> memos = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url,userName,password);
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Memo memo = new Memo(rs.getInt("memoId"),rs.getString("content"),
                        rs.getString("userId"));
                memos.add(memo);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return memos;
        }
    }
}
