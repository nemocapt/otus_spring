insert into author (`id`, `fname`, `lname`)
values (1, 'Jack', 'London');
insert into author (`id`, `fname`, `lname`)
values (2, 'Douglas', 'Adams');
insert into author (`id`, `fname`, `lname`)
values (3, 'James', 'Corey');

insert into genre (`id`, `name`)
values (1, 'science fiction');
insert into genre (`id`, `name`)
values (2, 'adventure');
insert into genre (`id`, `name`)
values (3, 'comic science fiction');

insert into book (`id`, `title`, `author_id`, `genre_id`)
values (1, 'The Hitchhiker''s Guide to the Galaxy', 2, 3);
insert into book (`id`, `title`, `author_id`, `genre_id`)
values (2, 'The Expanse', 3, 1);
insert into book (`id`, `title`, `author_id`, `genre_id`)
values (3, 'White Fang', 1, 2);
