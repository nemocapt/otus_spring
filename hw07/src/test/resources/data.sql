insert into author (`id`, `fname`, `lname`)
values (1, 'a', 'b'),
       (2, 'q', 'w');

insert into genre (`id`, `name`)
values (1, 'g'),
       (2, 'gg');

insert into book (`id`, `title`, `author_id`, `genre_id`)
values (1, 't', 1, 1);
