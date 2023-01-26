package db.jdbc;

import db.tmpl.BoardDataBase;
import db.tmpl.ConnectionManager;
import model.Board;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static webserver.utils.CommonUtils.LocalDateTimeToStr;
import static webserver.utils.CommonUtils.strToLocalDateTime;

public class JdbcBoardDatabase implements BoardDataBase {

    private ConnectionManager connectionManager;
    private JdbcBoardDatabase(){}

    public static JdbcBoardDatabase getInstance(){
        return JdbcBoardDatabase.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final JdbcBoardDatabase INSTANCE = new JdbcBoardDatabase();
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void addBoard(Board board) {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("insert into board(writerId, writerName, title, contents, createdAt) values (?, ?, ?, ?, ?)")) {
            ps.setString(1, board.getWriterId());
            ps.setString(2, board.getWriterName());
            ps.setString(3, board.getTitle());
            ps.setString(4, board.getContents());
            ps.setString(5, LocalDateTimeToStr(board.getCreatedAt()));
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Board> findBoardById(Integer boardId) {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("select * from board where boardId = ?")) {
            ps.setInt(1, boardId);
            ResultSet rs = ps.executeQuery();
            if(!rs.isBeforeFirst())
                return Optional.empty();
            rs.next();
            Board board = Board.builder()
                                .boardId(rs.getInt("boardId"))
                                .writerId(rs.getString("writerId"))
                                .writerName(rs.getString("writerName"))
                                .title(rs.getString("title"))
                                .contents(rs.getString("contents"))
                                .createdAt(strToLocalDateTime(rs.getString("createdAt")))
                                .build();
            return Optional.of(board);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteBoard(Integer boardId) {
        try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("delete from board where boardId = ?")) {
            ps.setInt(1, boardId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Board> findAll() {
       try(Connection conn = connectionManager.makeConnection(); PreparedStatement ps = conn.prepareStatement("select * from board")){
            List<Board> boardList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Board board = Board.builder()
                        .boardId(rs.getInt("boardId"))
                        .writerId(rs.getString("writerId"))
                        .writerName(rs.getString("writerName"))
                        .title(rs.getString("title"))
                        .contents(rs.getString("contents"))
                        .createdAt(strToLocalDateTime(rs.getString("createdAt")))
                        .build();
                boardList.add(board);
            }
            return boardList;
       } catch (SQLException e) {
           e.printStackTrace();
           throw new RuntimeException();
       }
    }
}
