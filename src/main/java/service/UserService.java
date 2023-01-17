package service;

import db.UserReposioryImpl;
import db.UserRepository;

public class UserService {

	private static UserService instance;
	private final UserRepository userRepository;

	private static UserService getInstance() {
		if (instance == null) {
			synchronized (UserService.class) {
				instance = new UserService();
			}
		}
		return instance;
	}

	public UserService() {
		this.userRepository = UserReposioryImpl.getInstance();
	}

	public boolean matchIdPassword(String userId, String password) {
		return userRepository.findUserById(userId).matchPassword(password);
	}
}
