CREATE TABLE board(
                      writer varchar(20) NOT NULL,
                      title varchar(50) NOT NULL,
                      content varchar(2000) NOT NULL,
                      PRIMARY KEY (writer)
) COMMENT '게시판';

INSERT INTO board (writer, title, content) values ('hi', 'myname', 'park_park_park');
INSERT INTO board (writer, title, content) values ('bye', 'your', 'turnturn');
INSERT INTO board (writer, title, content) values ('say', 'good', 'byemanmanman');
