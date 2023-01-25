package repository;

import java.util.Collection;

import model.Board;

public interface BoardRepository {

	public void addPost(Board board);

	public Collection<Board> findAll();
}
