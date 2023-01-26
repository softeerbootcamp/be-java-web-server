CREATE TABLE USERS(
    userId varchar(20) NOT NULL,
    password varchar(20) NOT NULL,
    name varchar(20) NOT NULL,
    email varchar(50),

    PRIMARY KEY (userId)
);

INSERT INTO USERS VALUES ("test","123","test","test@test.com");
INSERT INTO USERS VALUES ("park","234","park","park@park.com");
