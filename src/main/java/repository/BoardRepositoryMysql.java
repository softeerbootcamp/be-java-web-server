package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import model.Board;

public class BoardRepositoryMysql implements BoardRepository {

	private final DBManager dbManager;

	private static BoardRepositoryMysql instance;


	public static BoardRepositoryMysql getInstance() {
		if (instance == null) {
			synchronized (BoardRepositoryMysql.class) {
				instance = new BoardRepositoryMysql();
			}
		}
		return instance;
	}

	public BoardRepositoryMysql() {
		this.dbManager = DBManager.getInstance();
	}

	@Override
	public void addPost(Board board) {
		try (Connection conn = dbManager.getConnection();
			 PreparedStatement preparedStatement = conn.prepareStatement("insert into board values(?,?,?,?,?)");
		) {
			preparedStatement.setString(1, board.getBoardWriter());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(board.getBoardTime())); // 반대로 매핑할때는 toTimestamp()
			preparedStatement.setString(3, board.getBoardTitle());
			preparedStatement.setString(4, board.getBoardContents());
			preparedStatement.setInt(5, 0);

			if (preparedStatement.executeUpdate() != 1) {
				System.out.println("fail"); // logger로 바꾸기
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Board> findAll() {
		return null;
	}
}
