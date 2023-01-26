create table user (
        userId varchar(255),
        password varchar(255),
        name varchar(255),
        email varchar(255),
        primary key (userId)
    ) engine=InnoDB

create table memo (
        memoId bigint not null auto_increment,
        writer varchar(255),
        content varchar(255),
        createdAt datetime(6),
        primary key (memoId)
    ) engine=InnoDB