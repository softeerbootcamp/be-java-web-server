package model;

import java.time.LocalDateTime;

public class Board {
	private Long id;

	private String contents;

	private String title;

	private LocalDateTime time;

	private String writer;

	private Board(Long id, String contents, String title, LocalDateTime time, String writer) {
		this.id = id;
		this.contents = contents;
		this.title = title;
		this.time = time;
		this.writer = writer;
	}

	public static Board of(Long id, String boardContents, String boardTitle, LocalDateTime boardTime, String boardWriter) {
		return new Board(id, boardContents, boardTitle, boardTime, boardWriter);
	}

	public Long getId() {
		return id;
	}

	public String getContents() {
		return contents;
	}

	public String getTitle() {
		return title;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public String getWriter() {
		return writer;
	}
}
