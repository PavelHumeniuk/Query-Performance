DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS author_news;
DROP TABLE IF EXISTS tag_news;

CREATE TABLE author
(
  id bigint PRIMARY KEY NOT NULL,
  name varchar(200) NOT NULL
);
CREATE UNIQUE INDEX author_id_uindex ON author (id);
CREATE UNIQUE INDEX author_name_uindex ON author (name);

CREATE TABLE comment
(
  id bigint PRIMARY KEY NOT NULL,
  text varchar(800) NOT NULL,
  news_id bigint NOT NULL
);
CREATE UNIQUE INDEX comment_id_uindex ON comment (id);

CREATE TABLE tag
(
  id bigint PRIMARY KEY NOT NULL,
  text varchar(50) NOT NULL
);
CREATE UNIQUE INDEX tag_id_uindex ON tag (id);
CREATE UNIQUE INDEX tag_text_uindex ON tag (text);

CREATE TABLE news
(
  id bigint PRIMARY KEY NOT NULL,
  title varchar(200) NOT NULL,
  text varchar(2000) NOT NULL
);
CREATE UNIQUE INDEX news_id_uindex ON news (id);

CREATE TABLE author_news
(
  author_id bigint NOT NULL,
  news_id bigint NOT NULL
);

CREATE TABLE tag_news
(
  tag_id bigint NOT NULL,
  news_id bigint NOT NULL
);

insert into author values (1, 'Sir Joseph Rudyard Kipling');
insert into author values (2, 'Ernest Miller Hemingway');
insert into author values (3, 'Alexander Sergeyevich Pushkin');

insert into news values (1, 'How to survive in the jungle', 'Listen Akela and Bagheera');
insert into news values (2, 'How to be a fisherman', 'Be old');
insert into news values (3, 'How to survive after duel', '....');

insert into author_news values (1,1);
insert into author_news values (2,2);
insert into author_news values (3,3);

insert into tag values (1, 'children');
insert into tag values (2, 'story');
insert into tag values (3, 'novel');
insert into tag values (4, 'fiction');

insert into tag_news values (1,1);
insert into tag_news values (2,1);
insert into tag_news values (3,2);
insert into tag_news values (4,3);

insert into comment values (1, 'Listen Kaa, lol', 1);
insert into comment values (2, 'Boooring', 2);
insert into comment values (3, 'Cool story, Georges', 3)