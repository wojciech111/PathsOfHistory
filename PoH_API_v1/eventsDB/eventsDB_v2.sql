SET CLIENT_ENCODING TO 'utf8';
DROP DATABASE IF EXISTS eventsdb;
CREATE DATABASE eventsdb;

\c eventsdb;

SET CLIENT_ENCODING TO 'utf8';
CREATE TABLE events(
ID SERIAL PRIMARY KEY,
place VARCHAR,
date_of_event DATE,
point_in_time VARCHAR,
start_time VARCHAR,
time_of_discovery VARCHAR,
placelabel VARCHAR,
image VARCHAR,
coordinate_x NUMERIC,
coordinate_y NUMERIC,
instance_of VARCHAR,
instance_of_label VARCHAR,
sitelinken VARCHAR,
sitelinkpl VARCHAR,
sitelinkes VARCHAR,
sitelinkfr VARCHAR,
sitelinkde VARCHAR,
popularityen NUMERIC,
popularitypl NUMERIC,
popularityes NUMERIC,
popularityfr NUMERIC,
popularityde NUMERIC,
popularityzh NUMERIC,
popularityru NUMERIC,
popularitypt NUMERIC,
popularityit NUMERIC,
popularityar NUMERIC,
popularitysum NUMERIC
);
