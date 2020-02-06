drop table if exists film;

create table film
(
	title varchar(50) not null,
	release_date date not null,
	genre_list jsonb not null,
	average_rating double precision,
	time varchar(10),
	image bytea,
	description varchar(500) not null,
	id serial not null
		constraint film_pk
			primary key

);

