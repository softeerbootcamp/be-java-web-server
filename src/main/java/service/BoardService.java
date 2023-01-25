package service;

import java.time.LocalDateTime;

import model.Board;
import repository.BoardRepository;
import repository.BoardRepositoryMysql;

public class BoardService {

	private final BoardRepository boardRepository;
	private static BoardService instance;

	public BoardService() {
		this.boardRepository = BoardRepositoryMysql.getInstance();
	}

	public static BoardService getInstance() {
		if (instance == null) {
			synchronized (BoardService.class) {
				instance = new BoardService();
			}
		}
		return instance;
	}

	public void addUser(String boardTitle, String boardContents, String boardWriter) {
		Board board = Board.of(0L, boardContents, boardTitle, LocalDateTime.now(), boardWriter);
		boardRepository.addPost(board);
	}


}
