


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


select current_date;
select current_time;
select current_timestamp;

SELECT version();

select  user;
select  replace(gen_random_uuid()::varchar,'-','');
select  length(replace(gen_random_uuid()::varchar,'-',''));
select  generate_series(1,10)


select coalesce('1','2'); --postgresql
--select nvl('1','2'); --Oracle
--select ifnull('1','2'); --mysql



