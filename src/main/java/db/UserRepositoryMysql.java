package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import model.User;

public class UserRepositoryMysql implements UserRepository {
	private DBManager dbManager;

	public UserRepositoryMysql() {
		this.dbManager = DBManager.getInstance();
	}

	@Override
	public void addUser(User user) {
		try (Connection conn = dbManager.getConnection();
			 PreparedStatement preparedStatement = conn.prepareStatement("insert into user values(?,?,?,?,?)");
		) {
			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, user.getUserId());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getName());
			preparedStatement.setString(5, user.getEmail());

			if (preparedStatement.executeUpdate() != 1) {
				System.out.println("fail");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User findUserById(String UserId) {
		return null;
	}

	@Override
	public Collection<User> findAll() {
		return null;
	}

	@Override
	public boolean idExist(String userId) {
		return false;
	}
}
