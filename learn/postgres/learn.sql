


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
select Date 'today';
select Date 'tomorrow';


SELECT version();

select  user;
select  replace(gen_random_uuid()::varchar,'-','');
select  length(replace(gen_random_uuid()::varchar,'-',''));
select  generate_series(1,10);


select coalesce('1','2'); --postgresql
--select nvl('1','2'); --Oracle
--select ifnullifnull('1','2'); --mysql

select string_agg(distinct num,','order by num desc) from (
  select generate_series(1,5)::varchar num
) t_num;

select array_to_string(array_agg(distinct num order by num desc), ',') from (
select generate_series(1,5) num
) t_num;


select position('abd' in 'abcdab');
select strpos( 'abcdab','cd');
select  'ABcdef' ~* 'a.*f';  --  ~ 匹配   ~* 匹配(case ignroe)  !~ 不匹配


create unique index grouId_and_memberId on chat_member (group_id, member_id);
alter table if exists chat_message add constraint FKdgx6rii6jdb7vcelx67r1h2r1 foreign key (group_id) references chat_group;
alter table if exists chat_member add constraint grouId_and_memberId unique (group_id, member_id);

ALTER TABLE `USERINFO`
    ADD CONSTRAINT `FK_USER_ID` FOREIGN KEY (`USERID`) REFERENCES `USERACCOUNT` (`USERID`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;