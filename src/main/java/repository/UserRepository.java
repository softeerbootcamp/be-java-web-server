package repository;

import java.util.Collection;

import model.User;

public interface UserRepository {
	public void addUser(User user);

	public User findUserById(String UserId);

	public Collection<User> findAll();

	public boolean idExist(String userId);
}
