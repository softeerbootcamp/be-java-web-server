package db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import model.User;

public class UserReposioryImpl implements UserRepository {
	private static UserRepository instance;

	public static UserRepository getInstance() {
		if (instance == null) {
			synchronized (UserReposioryImpl.class) {
				instance = new UserReposioryImpl();
			}
		}
		return instance;
	}

	private Map<String, User> users = Maps.newHashMap();

	@Override
	public void addUser(User user) {
		users.put(user.getUserId(), user);
	}

	@Override
	public User findUserById(String userId) {
		return users.get(userId);
	}

	@Override
	public Collection<User> findAll() {
		return users.values();
	}

	@Override
	public boolean idExist(String userId) {
		return users.containsKey(userId);
	}
}
