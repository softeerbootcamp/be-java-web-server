package model;

import java.time.LocalDateTime;

public class Board {
	private Long id;

	private String boardContents;

	private String boardTitle;

	private LocalDateTime boardTime;

	private String boardWriter;

	private Board(Long id, String boardContents, String boardTitle, LocalDateTime boardTime, String boardWriter) {
		this.id = id;
		this.boardContents = boardContents;
		this.boardTitle = boardTitle;
		this.boardTime = boardTime;
		this.boardWriter = boardWriter;
	}

	public Board of(Long id, String boardContents, String boardTitle, LocalDateTime boardTime, String boardWriter) {
		return new Board(id, boardContents, boardTitle, boardTime, boardWriter);
	}

	public Long getId() {
		return id;
	}

	public String getBoardContents() {
		return boardContents;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public LocalDateTime getBoardTime() {
		return boardTime;
	}

	public String getBoardWriter() {
		return boardWriter;
	}
}
