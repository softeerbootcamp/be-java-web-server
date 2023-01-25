package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.User;

public class UserRepositoryMysql implements UserRepository {
	private final DBManager dbManager;

	private static UserRepositoryMysql instance;

	public UserRepositoryMysql() {
		this.dbManager = DBManager.getInstance();
	}

	public static UserRepositoryMysql getInstance() {
		if (instance == null) {
			synchronized (UserRepositoryMysql.class) {
				instance = new UserRepositoryMysql();
			}
		}
		return instance;
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
	public User findUserById(String userId) {
		try (Connection conn = dbManager.getConnection();
			 PreparedStatement preparedStatement = conn.prepareStatement(
				 "select 1 from user where user_id = ?");
		) {
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			return User.of(resultSet.getString("user_id"), resultSet.getString("user_password"),
				resultSet.getString("user_name"), resultSet.getString("user_email"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<User> findAll() {
		try (Connection conn = dbManager.getConnection();
			 PreparedStatement preparedStatement = conn.prepareStatement("select * from user");
		) {
			ResultSet resultSet = preparedStatement.executeQuery();
			List<User> users = new ArrayList<>();
			while (resultSet.next()) {
				String userId = resultSet.getString("user_id");
				String userPassword = resultSet.getString("user_password");
				String userEmail = resultSet.getString("user_email");
				String userName = resultSet.getString("user_name");
				users.add(User.of(userId, userPassword, userEmail, userName));
			}

			return users;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean idExist(String userId) {

		try (Connection conn = dbManager.getConnection();
			 PreparedStatement preparedStatement = conn.prepareStatement(
				 "select 1 from user where user_id = ?");
		) {
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
