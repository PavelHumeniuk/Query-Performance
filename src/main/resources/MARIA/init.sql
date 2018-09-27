DROP TABLE IF EXISTS test.author;
DROP TABLE IF EXISTS test.comment;
DROP TABLE IF EXISTS test.tag;
DROP TABLE IF EXISTS test.news;
DROP TABLE IF EXISTS test.author_news;
DROP TABLE IF EXISTS test.tag_news;

CREATE TABLE test.author
(
  id bigint PRIMARY KEY NOT NULL,
  name varchar(200) NOT NULL
);
CREATE UNIQUE INDEX author_id_uindex ON test.author (id);
CREATE UNIQUE INDEX author_name_uindex ON test.author (name);

CREATE TABLE test.comment
(
  id bigint PRIMARY KEY NOT NULL,
  text varchar(800) NOT NULL,
  news_id bigint NOT NULL
);
CREATE UNIQUE INDEX comment_id_uindex ON test.comment (id);

CREATE TABLE test.tag
(
  id bigint PRIMARY KEY NOT NULL,
  text varchar(50) NOT NULL
);
CREATE UNIQUE INDEX tag_id_uindex ON test.tag (id);
CREATE UNIQUE INDEX tag_text_uindex ON test.tag (text);

CREATE TABLE test.news
(
  id bigint PRIMARY KEY NOT NULL,
  title varchar(200) NOT NULL,
  text varchar(2000) NOT NULL
);
CREATE UNIQUE INDEX news_id_uindex ON test.news (id);

CREATE TABLE test.author_news
(
  author_id bigint NOT NULL,
  news_id bigint NOT NULL
);

CREATE TABLE test.tag_news
(
  tag_id bigint NOT NULL,
  news_id bigint NOT NULL
);

insert into test.author values (1, 'Sir Joseph Rudyard Kipling');
insert into test.author values (2, 'Ernest Miller Hemingway');
insert into test.author values (3, 'Alexander Sergeyevich Pushkin');

insert into test.news values (1, 'How to survive in the jungle', 'Listen Akela and Bagheera');
insert into test.news values (2, 'How to be a fisherman', 'Be old');
insert into test.news values (3, 'How to survive after duel', '....');

insert into test.author_news values (1,1);
insert into test.author_news values (2,2);
insert into test.author_news values (3,3);

insert into test.tag values (1, 'children');
insert into test.tag values (2, 'story');
insert into test.tag values (3, 'novel');
insert into test.tag values (4, 'fiction');

insert into test.tag_news values (1,1);
insert into test.tag_news values (2,1);
insert into test.tag_news values (3,2);
insert into test.tag_news values (4,3);

insert into test.comment values (1, 'Listen Kaa, lol', 1);
insert into test.comment values (2, 'Boooring', 2);
insert into test.comment values (3, 'Cool story, Georges', 3)