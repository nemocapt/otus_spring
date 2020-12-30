drop table if exists author;
create table author
(
    id    bigint auto_increment primary key,
    fname varchar(255) not null,
    lname varchar(255) not null,
    unique (fname, lname)
);

drop table if exists genre;
create table genre
(
    id   bigint auto_increment primary key,
    name varchar(255) not null,
    unique (name)
);

drop table if exists book;
create table book
(
    id        bigint auto_increment primary key,
    title     varchar(255) not null,
    author_id bigint       not null,
    genre_id  bigint       not null,
    foreign key (author_id) references author (id),
    foreign key (genre_id) references genre (id),
    unique (title)
);
