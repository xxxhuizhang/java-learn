

docker exec -it postgres psql -U  postgres
docker exec -it postgres psql mydb -U  postgres

createdb  mydb;
dropdb mydb;


1、相当与mysql的show databases;
select datname from pg_database;

2、相当于mysql的show tables;
SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';
public 是默认的schema的名字


3、相当与mysql的describe table_name;
SELECT column_name FROM information_schema.columns WHERE table_name ='table_name';
'table_name'是要查询的表的名字




SELECT current_date;
SELECT version();



CREATE TABLE weather (
   city varchar(80) references cities(name),
	temp_lo int, -- low temperature
	temp_hi int, -- high temperature
	prcp real, -- precipitation
	date date
);


COPY weather FROM '/home/user/weather.txt';

INSERT INTO weather VALUES ('San Francisco', 46, 50, 0.25,'1994-11-27');
INSERT INTO weather VALUES ('Berkeley', 45, 53, 0.0, '1994-11-28');
--INSERT INTO weather (city, temp_lo, temp_hi, prcp, date) VALUES ('San Francisco', 43, 57, 0.0, '1994-11-29');
INSERT INTO weather (date, city, temp_hi, temp_lo) VALUES ('1994-11-29', 'Hayward', 54, 37);


CREATE TABLE cities (
 name varchar(80) primary key,
 location point
);

INSERT INTO cities VALUES ('San Francisco', '(-194.0, 53.0)');


DROP TABLE tablename;


SELECT city, (temp_hi+temp_lo)/2 AS temp_avg, date FROM weather;


SELECT *  FROM weather JOIN cities ON city = name;
SELECT *  FROM weather, cities WHERE city = name;

SELECT w1.city, w1.temp_lo AS low, w1.temp_hi AS high,
 w2.city, w2.temp_lo AS low, w2.temp_hi AS high
 FROM weather w1 JOIN weather w2
 ON w1.temp_lo < w2.temp_lo AND w1.temp_hi > w2.temp_hi;

-- VIEW 

 CREATE VIEW myview AS
 SELECT name, temp_lo, temp_hi, prcp, date, location
 FROM weather, cities
 WHERE city = name;

SELECT * FROM myview;


-- TRANSACTIONS

BEGIN;
UPDATE accounts SET balance = balance - 100.00
 WHERE name = 'Alice';
SAVEPOINT my_savepoint;
UPDATE accounts SET balance = balance + 100.00
 WHERE name = 'Bob';
-- oops ... forget that and use Wally's account
ROLLBACK TO my_savepoint;
UPDATE accounts SET balance = balance + 100.00


 -- Window Functions

 SELECT sum(salary) OVER w, avg(salary) OVER w
 FROM empsalary
 WINDOW w AS (PARTITION BY depname ORDER BY salary DESC);

SELECT stuff,
       count(*) OVER() AS total_count
FROM table
WHERE condition
ORDER BY stuff OFFSET 40 LIMIT 20

 

--  Inheritance

CREATE TABLE cities (
 name text,
 population real,
 elevation int -- (in ft)
);

CREATE TABLE capitals (
 state char(2) UNIQUE NOT NULL
) INHERITS (cities);



aggregate_name (expression [ , ... ] [ order_by_clause ] ) [ FILTER( WHERE filter_clause ) ]
aggregate_name (ALL expression [ , ... ] [ order_by_clause ] ) [ FILTER ( WHERE filter_clause ) ]
aggregate_name (DISTINCT expression [ , ... ] [ order_by_clause ] ) [ FILTER ( WHERE filter_clause ) ]
aggregate_name ( * ) [ FILTER ( WHERE filter_clause ) ]
aggregate_name ( [ expression [ , ... ] ] ) WITHIN GROUP( order_by_clause ) [ FILTER ( WHERE filter_clause ) ]















